package com.fudian.api.system.enums;

import com.fudian.common.pojo.CommonResult;

/**
 * @Author: WangYuZhuo
 * @Date: 2019/12/31 15:50
 * @Desc: 部门管理接口返回值枚举集合
 */
public class DeptEnum {

    public enum CheckDept{
        /**
         * 返回值
         */
        ERROR(500,"操作失败"),SUCCESS(200,"操作成功"),EXIST_DEPT(501,"存在子级部门"),EXIST_USER(501,"存在人员信息");

        private int code;

        private String msg;

        private CommonResult result;

        CheckDept(int code, String msg) {
            this.code = code;
            this.msg = msg;
            result = new CommonResult();
            result.setCode(code);
            result.setMsg(msg);
        }

        public CommonResult getResult() {
            return result;
        }

        public void setResult(CommonResult result) {
            this.result = result;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
