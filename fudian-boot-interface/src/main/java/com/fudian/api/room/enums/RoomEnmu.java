package com.fudian.api.room.enums;

import com.fudian.common.pojo.CommonResult;

/**
 * @Author: WangYuZhuo
 * @Date: 2019/12/31 15:10
 * @Desc: 库房管理中用到的返回值枚举
 */
public class RoomEnmu {

    /**
     * 档案入库枚举
     */
    public enum A01Up{

        /**
         * 接口返回信息
         */
        ERROR(500,"操作失败"),SUCCESS(200,"操作成功"),UPPER_LIMIT(501,"库房容量已达上限"),EXIST(501,"当前档案已存在其他位置");

        private int code;

        private String msg;

        private CommonResult result;

        A01Up(int code, String msg) {
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

    /**
     * 档案出库枚举
     */
    public enum A01Down{

        /**
         * 接口返回信息
         */
        ERROR(500,"操作失败"),SUCCESS(200,"操作成功"),EXIST_POS(501,"档案位置不存在"),EXIST_A01(501,"档案不在库房中");

        private int code;

        private String msg;

        private CommonResult result;

        A01Down(int code, String msg) {
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

    /**
     * 删除库房结构
     */
    public enum StructDelete{

        /**
         * 接口返回信息
         */
        ERROR(500,"操作失败"),SUCCESS(200,"操作成功"),EXIST_COLUMN(501,"节点中存在档案");

        private int code;

        private String msg;

        private CommonResult result;

        StructDelete(int code, String msg) {
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

    /**
     * 更改档案位置枚举
     */
    public enum UpdateA01Pos{

        /**
         * 接口返回信息
         */
        ERROR(500,"操作失败"),SUCCESS(200,"操作成功"),UPPER_LIMIT(501,"位置容量已达上限");

        private int code;

        private String msg;

        private CommonResult result;

        UpdateA01Pos(int code, String msg) {
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

    /**
     * 删除库房枚举
     */
    public enum DeleteRoom{

        /**
         * 接口返回信息
         */
        ERROR(500,"操作失败"),SUCCESS(200,"操作成功"),UPPER_LIMIT(501,"库房内存在档案");

        private int code;

        private String msg;

        private CommonResult result;

        DeleteRoom(int code, String msg) {
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
