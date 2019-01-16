package com.ruoyi.fac.service.impl;

import com.ruoyi.common.support.Convert;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.mapper.ProductMapper;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.ProductImgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
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
        if (StringUtils.isNotEmpty(product.getImgPath())) {
            String imgPath = product.getImgPath();
            if (imgPath.startsWith(",")) {
                imgPath = imgPath.substring(1);
            }
            product.setPicture(imgPath);
        }
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
        if (StringUtils.isNotEmpty(product.getImgPath())) {
            String imgPath = product.getImgPath();
            if (imgPath.startsWith(",")) {
                imgPath = imgPath.substring(1);
            }
            product.setPicture(imgPath);
        }
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
        vo.setCode("-1");
        if (product == null || product.getId() == null) {
            return vo;
        }
        Product dstProduct = this.productMapper.selectProductById(product.getId());
        String pictures = dstProduct.getPicture();
        if (StringUtils.isNotEmpty(pictures)) {
            vo.setCode("0");
            String[] imgPaths = pictures.split(",");
            vo.setImgPaths(imgPaths);
            List<String> imgList = Arrays.asList(imgPaths);
            List<Map<String, Object>> cfgs = new ArrayList<>();
            Map<String, Object> cfg;
            String imgPath;
            for (int i = 0, size = imgList.size(); i < size; i++) {
                cfg = new HashMap<>(16);
                imgPath = imgList.get(i);
                cfg.put("caption", getFileName(imgPath));
                cfg.put("size", getFileSize(imgPath));
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

    /**
     * 获取文件名称
     *
     * @param fileUrl 网络资源文件 地址
     * @return 名称
     */
    public String getFileName(String fileUrl) {
        String fileName = "";
        if (org.apache.commons.lang3.StringUtils.isBlank(fileUrl) || fileUrl.indexOf("/") < 0 || fileUrl.length() <= 1) {
            return fileName;
        }
        fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        return fileName;
    }

    /**
     * 获取网络资源文件大小
     *
     * @param fileUrl 网络资源文件 地址
     * @return 文件大小
     */
    public String getFileSize(String fileUrl) {
        String fileSize = "";
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection urlFile = (HttpURLConnection) url.openConnection();
            //根据响应获取文件大小
            int fileLength = urlFile.getContentLength();
            if (urlFile.getResponseCode() >= 400) {
                logger.info("服务器响应错误,fileUrl:{}", fileUrl);
                return fileSize;
            }
            if (fileLength <= 0) {
                logger.info("无法获知文件大小,fileUrl:{}", fileUrl);
                return fileSize;
            }
            DecimalFormat df = new DecimalFormat("######0");
            Double size = Double.parseDouble(String.valueOf(fileLength));
            fileSize = df.format(size);
        } catch (Exception ex) {
            logger.info("获知文件大小操作异常,fileUrl:{}", fileUrl, ex);
        }

        return fileSize;
    }

}
