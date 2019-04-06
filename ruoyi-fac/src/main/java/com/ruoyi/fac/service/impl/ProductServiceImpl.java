package com.ruoyi.fac.service.impl;

import com.ruoyi.common.support.Convert;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Order;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.enums.FocusStatus;
import com.ruoyi.fac.enums.ProductStatus;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.OrderMapper;
import com.ruoyi.fac.mapper.ProductCategoryMapper;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.fac.util.DecimalUtils;
import com.ruoyi.fac.util.FacFileUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.ProductImgVo;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.fac.vo.condition.QueryGoodVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商品 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class ProductServiceImpl implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
    public Product selectProductById(Long id) {
        Product product = productMapper.selectProductById(id);
        if (product.getRushStart() != null) {
            product.setRushStartStr(TimeUtils.date2Str(product.getRushStart(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (product.getRushEnd() != null) {
            product.setRushEndStr(TimeUtils.date2Str(product.getRushEnd(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (product.getWriteoffStart() != null) {
            product.setWriteoffStartStr(TimeUtils.date2Str(product.getWriteoffStart(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }
        if (product.getWriteoffEnd() != null) {
            product.setWriteoffEndStr(TimeUtils.date2Str(product.getWriteoffEnd(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM));
        }

        return product;
    }

    /**
     * 查询商品列表
     *
     * @param product 商品信息
     * @return 商品集合
     */
    @Override
    public List<Product> selectProductList(Product product) {
        product.setIsDeleted(0);
        return productMapper.selectProductList(product);
    }

    /**
     * 新增商品
     *
     * @param product 商品信息
     * @return 结果
     */
    @Override
    public int insertProduct(Product product) {
        product.setOrderCount(0);
        return productMapper.insertProduct(product);
    }

    /**
     * 修改商品
     *
     * @param product 商品信息
     * @return 结果
     */
    @Override
    public int updateProduct(Product product) throws FacException {
        // 编辑场景下用introductionEdit字段存储最新的商品介绍内容
        product.setIntroduction(product.getIntroductionEdit());
        Product productdb = this.productMapper.selectProductById(product.getId());
        if (productdb == null || productdb.getIsDeleted().intValue() == 1) {
            throw new FacException("当前商品已被删除，请确认");
        }
        // 商品被下架前需要校验当前商品是否存在于待付款的订单中
        if (productdb.getStatus().equals(ProductStatus.UPPER_SHELF.getValue())
                && product.getStatus().equals(ProductStatus.LOWER_SHELF.getValue())) {
            // 当前商品处于待付款的订单
            List<Long> prodIds = new ArrayList<>();
            prodIds.add(productdb.getId());
            List<Integer> status = new ArrayList<>();
            status.add(Integer.valueOf("0"));
            List<Order> pendingPaymentOrders = this.orderMapper.selectProductsByProdAndStatus(prodIds, status);
            if (CollectionUtils.isNotEmpty(pendingPaymentOrders)) {
                StringBuilder pendingPayment = new StringBuilder("当前商品在以下订单中处于待付款状态，请处理订单后再下架：\n");
                for (Order order : pendingPaymentOrders) {
                    pendingPayment.append(order.getOrderNo()).append(",");
                }
                pendingPayment = pendingPayment.deleteCharAt(pendingPayment.toString().length() - 1);
                throw new FacException(pendingPayment.toString());
            }
        }
//        SysUser user = ShiroUtils.getSysUser();
        return productMapper.updateProduct(product);
    }

    /**
     * 删除商品对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductByIds(String ids) {
        return productMapper.deleteProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 获取当前商品对应图片信息
     *
     * @param product
     * @return
     */
    @Override
    public ProductImgVo getProductImgs(Product product) {
        ProductImgVo vo = new ProductImgVo();
        vo.setCode(FacConstant.AJAX_CODE_FAIL);
        if (product == null || product.getId() == null) {
            return vo;
        }
        Product dstProduct = this.productMapper.selectProductById(product.getId());
        if (dstProduct == null) {
            return vo;
        }
        String pictures = dstProduct.getPicture();
        if (StringUtils.isNotEmpty(pictures)) {
            vo.setCode(FacConstant.AJAX_CODE_SUCCESS);
            String[] imgPaths = pictures.split(",");
            vo.setImgPaths(imgPaths);
            List<Map<String, Object>> cfgs = new ArrayList<>();
            Map<String, Object> cfg;
            String imgPath;
            for (int i = 0, size = imgPaths.length; i < size; i++) {
                cfg = new HashMap<>(16);
                imgPath = imgPaths[i];
                cfg.put("caption", FacFileUtils.getInstance().getFileName(imgPath));
                cfg.put("size", FacFileUtils.getInstance().getFileSize(imgPath));
                // 删除当前图片对应的参数: 商品id + "+" + imgPath
                String key = dstProduct.getId().toString() + FacConstant.SEPARATOR_SEMICOLON + imgPath;
                cfg.put("key", key);
                cfg.put("width", "200px");
                // 每个图片元素上的小删除按钮对应的接口url地址
                cfg.put("url", "deletePic");
                cfgs.add(cfg);
            }
            vo.setCfg(cfgs);
        }

        return vo;
    }

    /**
     * 查询指定条件下的商品数据
     *
     * @param categoryId
     * @param nameLike
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<GoodVo> goodsList(String categoryId, String nameLike, Integer page, Integer pageSize) {
        List<GoodVo> goodVos = new ArrayList<>();
        QueryGoodVo vo = new QueryGoodVo();
        if (StringUtils.isNotEmpty(categoryId) && !StringUtils.equals("0", categoryId)) {
            vo.setCategoryId(categoryId);
        }
        if (StringUtils.isNotEmpty(nameLike)) {
            vo.setName(nameLike);
        }
        if (page != null && pageSize != null && pageSize > 0) {
            vo.setPage(pageSize * (page - 1));
            vo.setPageSize(pageSize);
        }
        List<Product> products = this.productMapper.goodsList(vo);
        if (!CollectionUtils.isEmpty(products)) {
            GoodVo goodVo = null;
            for (int i = 0, size = products.size(); i < size; i++) {
                Product product = products.get(i);
                // 处于下架状态的商品不展示
                if (ProductStatus.LOWER_SHELF.getValue().equals(product.getStatus())) {
                    continue;
                }
                goodVo = this.convertGoodVo(product);
                goodVos.add(goodVo);

            }
        }

        return goodVos;
    }

    /**
     * 商品详情
     *
     * @param id
     */
    @Override
    public GoodDetailVo goodsDetail(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        GoodDetailVo vo = new GoodDetailVo();
        Product product = this.productMapper.selectProductById(Long.valueOf(id));
        if (product == null || product.getIsDeleted() == 1) {
            return null;
        }
        // 当前商品所属商品分类信息
        if (product.getCategoryId() != null) {
            ProductCategory productCategory = this.productCategoryMapper.selectProductCategoryById(product.getCategoryId());
            if (productCategory != null) {
                CategoryVo category = new CategoryVo();
                category.setDateAdd(TimeUtils.date2Str(productCategory.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                category.setIcon(productCategory.getPicture());
                category.setId(productCategory.getId());
                category.setUse(FocusStatus.VISIBLE.getValue().equals(productCategory.getStatus()));
                category.setKey("");
                category.setName(productCategory.getName());
                category.setPaixu(productCategory.getSort());
                category.setPid(0);
                category.setType("");
                category.setUserId(productCategory.getOperatorId());
            }
        }
        // 商品图片
        if (StringUtils.isNotEmpty(product.getPicture())) {
            List<PicsVo> pics = new ArrayList<>();
            List<String> picList = Arrays.asList(product.getPicture().split(","));
            for (int i = 0, size = picList.size(); i < size; i++) {
                PicsVo picsVo = new PicsVo();
                pics.add(picsVo);
                picsVo.setGoodsId(product.getId());
                picsVo.setId(product.getId() + i);
                picsVo.setPic(picList.get(i));
                picsVo.setUserId(product.getOperatorId());
            }
            vo.setPics(pics);
        }
        // 商品介绍
        vo.setContent(product.getIntroduction());
        // 商品基本信息
        GoodVo basicInfo = this.convertGoodVo(product);
        vo.setBasicInfo(basicInfo);

        return vo;
    }

    /**
     * 商品价格
     *
     * @param id
     * @return
     */
    @Override
    public GoodsPriceVo goodPrice(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Product product = this.productMapper.selectProductById(Long.valueOf(id));
        if (product == null) {
            return null;
        }
        GoodsPriceVo vo = new GoodsPriceVo();
        vo.setGoodsId(product.getId());
        vo.setId(product.getId());
        vo.setOriginalPrice(Double.valueOf(product.getOriginalPrice().toString()));
        vo.setPrice(Double.valueOf(product.getPrice().toString()));
        vo.setPingtuanPrice(0.00);
        vo.setPropertyChildIds("");
        vo.setScore(0);
        vo.setStores(product.getInventoryQuantity());
        vo.setUserId(product.getOperatorId());

        return vo;
    }

    /**
     * 删除商品信息
     *
     * @param key 需要删除的图片 : 商品id + "+" + imgPath
     * @return 结果
     */
    @Override
    public int deletePic(String key) {
        // 1.删除db中对应的数据
        if (StringUtils.isBlank(key) || key.indexOf(FacConstant.SEPARATOR_SEMICOLON) < 0 || key.split(FacConstant.SEPARATOR_SEMICOLON).length < 2) {
            return 0;
        }
        String prodId = key.split(FacConstant.SEPARATOR_SEMICOLON)[0];
        String fullImgPath = key.split(FacConstant.SEPARATOR_SEMICOLON)[1];
        Product product = this.productMapper.selectProductById(Long.valueOf(prodId));
        if (product == null) {
            return 0;
        }
        String picture = product.getPicture();
        if (StringUtils.isBlank(picture)) {
            return 0;
        }
        String[] pictureArr = picture.split(",");
        List<String> newImgPaths = new ArrayList<>();
        for (int i = 0, size = pictureArr.length; i < size; i++) {
            if (!StringUtils.equals(fullImgPath, pictureArr[i])) {
                newImgPaths.add(pictureArr[i]);
            }
        }
        if (CollectionUtils.isNotEmpty(newImgPaths)) {
            picture = StringUtils.join(newImgPaths, ",");
        } else {
            picture = "";
        }
        product.setPicture(picture);
        product.setUpdateTime(new Date());
        this.productMapper.updateProduct(product);

        // 2.删除cos中对应的数据 : 暂时不删除cos里的图片

        return 1;
    }

    private GoodVo convertGoodVo(Product product) {
        GoodVo goodVo = new GoodVo();
        goodVo.setBarCode("");
        goodVo.setCategoryId(product.getCategoryId());
        goodVo.setCharacteristic("");
        // 优先奖励积分：积分不为0，就奖励积分，积分为0，再看分销奖金，分销奖金再为0，就没有奖赏
        String commission = "0";
        int commissionType = 0;
        if (product.getBonusPoints() != null && product.getBonusPoints().intValue() != 0) {
            commission = product.getBonusPoints().toString();
            commissionType = 1;
        }
        if (StringUtils.equals("0", commission) && product.getDistribution() != null
                && DecimalUtils.compare(product.getDistribution(), new BigDecimal("0.00")) != 0) {
            commission = product.getDistribution().toString();
            commissionType = 2;
        }
        // 分享有赏送的积分
        goodVo.setCommission(Double.valueOf(commission));
        // 分享是否有赏：0-没有赏，1-赏积分，2-赏现金
        goodVo.setCommissionType(commissionType);
        goodVo.setDateAdd(TimeUtils.date2Str(product.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        goodVo.setDateStart(TimeUtils.date2Str(product.getRushStart(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        goodVo.setDateEnd(TimeUtils.date2Str(product.getRushEnd(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        goodVo.setDateEndCountDown("2019年");
        if (product.getUpdateTime() != null) {
            goodVo.setDateUpdate(TimeUtils.date2Str(product.getUpdateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
        }
        goodVo.setGotScore(0);
        goodVo.setGotScoreType(0);
        goodVo.setId(product.getId());
        goodVo.setKanjia(false);
        goodVo.setKanjiaPrice(0.00);
        goodVo.setLogisticsId(0);
        goodVo.setMinPrice(Double.valueOf(product.getPrice().toString()));
        goodVo.setMinScore(0);
        goodVo.setName(product.getName());
        goodVo.setNumberFav(0);
        goodVo.setNumberGoodReputation(0);
        goodVo.setNumberOrders(product.getOrderCount());
        goodVo.setNumberSells(product.getSales());
        goodVo.setOriginalPrice(Double.valueOf(product.getOriginalPrice().toString()));
        goodVo.setPaixu(product.getSort());
        // 默认取第一张图片
        if (StringUtils.isNotEmpty(product.getPicture())) {
            String pics = product.getPicture();
            goodVo.setPic(pics.split(",")[0]);
        }
        goodVo.setPingtuan(false);
        goodVo.setPingtuanPrice(0.00);
        goodVo.setPropertyIds("");
        goodVo.setRecommendStatus(1);
        goodVo.setRecommendStatusStr("推荐");
        goodVo.setShopId(product.getBusinessId());
        goodVo.setStatus(product.getStatus());
        goodVo.setStatusStr(ProductStatus.getNameByCode(product.getStatus().toString()));
        goodVo.setStores(product.getInventoryQuantity());
        goodVo.setUserId(product.getOperatorId());
        goodVo.setVideoId("");
        goodVo.setViews(1);
        goodVo.setWeight(0.00);

        return goodVo;
    }
}
