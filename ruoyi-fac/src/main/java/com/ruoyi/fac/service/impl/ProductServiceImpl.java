package com.ruoyi.fac.service.impl;

import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.enums.FocusStatus;
import com.ruoyi.fac.enums.ProductStatus;
import com.ruoyi.fac.mapper.ProductCategoryMapper;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.fac.util.FacFileUtils;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.ProductImgVo;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.fac.vo.condition.QueryGoodVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
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
        this.resetProductImg(product);
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
    public int updateProduct(Product product) {
        this.resetProductImg(product);
        // 编辑场景下用introductionEdit字段存储最新的商品介绍内容
        product.setIntroduction(product.getIntroductionEdit());
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
        // zgf
//        String pictures = dstProduct.getPicture();
        String pictures = FacConstant.TEST_IMG_URL;
        if (StringUtils.isNotEmpty(pictures)) {
            vo.setCode(FacConstant.AJAX_CODE_SUCCESS);
            String[] imgPaths = pictures.split(",");
            vo.setImgPaths(imgPaths);
            List<String> imgList = Arrays.asList(imgPaths);
            List<Map<String, Object>> cfgs = new ArrayList<>();
            Map<String, Object> cfg;
            String imgPath;
            for (int i = 0, size = imgList.size(); i < size; i++) {
                cfg = new HashMap<>(16);
                imgPath = imgList.get(i);
                cfg.put("caption", FacFileUtils.getInstance().getFileName(imgPath));
                cfg.put("size", FacFileUtils.getInstance().getFileSize(imgPath));
                cfg.put("key", (i + 1));
                cfg.put("width", "200px");
                // 每个图片元素上的小删除按钮对应的接口url地址
                cfg.put("url", "");
                cfgs.add(cfg);
            }
            vo.setCfg(cfgs);
        }

        return vo;
    }

    private void resetProductImg(Product product) {
        if (product != null && StringUtils.isNotEmpty(product.getImgPath())) {
            String imgPath = product.getImgPath();
            if (imgPath.startsWith(",")) {
                imgPath = imgPath.substring(1);
            }
            product.setPicture(imgPath);
        }
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
            vo.setPage(page);
            vo.setPageSize(pageSize);
        }
        List<Product> products = this.productMapper.goodsList(vo);
        if (!CollectionUtils.isEmpty(products)) {
            GoodVo goodVo = null;
            for (int i = 0, size = products.size(); i < size; i++) {
                Product product = products.get(i);
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

    private GoodVo convertGoodVo(Product product) {
        GoodVo goodVo = new GoodVo();
        goodVo.setBarCode("");
        goodVo.setCategoryId(product.getCategoryId());
        goodVo.setCharacteristic("");
        goodVo.setCommission(10.00);
        goodVo.setCommissionType(1);
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
