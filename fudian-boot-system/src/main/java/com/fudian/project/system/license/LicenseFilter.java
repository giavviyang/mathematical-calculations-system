package com.fudian.project.system.license;

import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.PasswordUtils;
import com.fudian.project.system.pojo.LicenseKey;
import com.fudian.project.system.service.LicenseKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/7/21 10:04
 * @Desc:
 */
//@Component
public class LicenseFilter extends OncePerRequestFilter {

    @Autowired
    LicenseKeyService licenseKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        List<LicenseKey> list = licenseKeyService.getLicenses();
        CommonResult commonResult = null;
        if(list.size() == 0 || list.size()>1){
            //多个license文件或者缺少license文件
            commonResult = CommonResult.error("找不到license文件");
        }
        LicenseKey licenseKey = list.get(0);

        String license = PasswordUtils.AESEncode(licenseKey.getLicense(), PasswordUtils.LICENSE, PasswordUtils.IV);

        boolean b = PasswordUtils.checkLicense(license);
        if(b){
            //license文件失效
            commonResult = CommonResult.error("license文件失效");
        }

        if(commonResult == null){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}
