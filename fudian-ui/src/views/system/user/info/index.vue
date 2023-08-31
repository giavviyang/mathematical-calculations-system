<template>
  <el-card>

    <el-card class="box-card">
    <div class="item text" style="float:left ; width:20%; height:100%; border-right:1px solid black; margin:0px auto" >
<!--      临时申请-->
      <img src="@/assets/image/temp.png" width="120px" height="100px"/>
    </div>
    <div v-for="o in tableData1" class="text item" style="float:left ; width:70%; height:100%;">
      {{ o.detail }},<a class="look" @click="temApplic">查看</a>
    </div>
  </el-card>

    <el-card class="box-card">
      <div class="item text" style="float:left ; width:20%; height:100%; border-right:1px solid black">
<!--        电子档案-->
<!--        <i class="el-icon-tickets"></i>-->
        <img src="@/assets/image/electron.png" width="120px" height="100px"/>
      </div>
      <div v-for="o in tableData2" class="text item" style="float:left ; width:80%; height:100%;">
        {{ o.detail }},<a class="look" @click="elecApplic">查看</a>
      </div>
    </el-card>

    <el-card class="box-card">
      <div class="item text" style="float:left ; width:20%; height:100%; border-right:1px solid black">
<!--        查阅申请-->
<!--        <i class="el-icon-tickets"></i>-->
        <img src="@/assets/image/browse.png" width="120px" height="100px"/>
      </div>
      <div v-for="o in tableData3" class="text item" style="float:left ; width:80%; height:100%;">
        {{ o.detail }},<a class="look" @click="browseApplic">查看</a>
      </div>
    </el-card>

    <el-card class="box-card">
      <div class="item text" style="float:left ; width:20%; height:100%; border-right:1px solid black">
<!--        借阅申请-->
<!--        <i class="el-icon-tickets"></i>-->
        <img src="@/assets/image/borrow.png" width="120px" height="100px"/>
      </div>
      <div v-for="o in tableData4" class="text item" style="float:left ; width:80%; height:100%;">
        {{ o.detail }},<a class="look" @click="borrowApplic">查看</a>
      </div>
    </el-card>

    <el-card class="box-card">
      <div class="item text" style="float:left ; width:20%; height:100%; border-right:1px solid black">
<!--        材料催缴-->
<!--        <i class="el-icon-tickets"></i>-->
        <img src="@/assets/image/ask.png" width="120px" height="100px"/>
      </div>
      <div v-for="o in tableData5" class="text item" style="float:left ; width:80%; height:100%;">
        {{ o.detail }},<a class="look" @click="askApplic">查看</a>
      </div>
    </el-card>
  </el-card>
</template>

<script>
  import {queryTemp,queryElec, queryBrowse, queryBorrow,queryAskApplic} from "@/api/message/info"

  export default {
    name: "Info",
    data() {
      return {
        imgUrl:"@/assets/image/borrow.png",
        user: {},
        roleGroup: {},
        postGroup: {},
        activeTab: "userinfo",
        tableData1: [],
        tableData2: [],
        tableData3: [],
        tableData4: [],
        tableData5: [],
      };
    },
    created() {
      /*this.getTemp();
      this.getElec();
      this.getBrowse();
      this.getBorrow();
      this.getAsk();*/
    },
    methods: {
      getTemp() {
        queryTemp().then(response => {
          this.tableData1 = response.rows;
        });
      },
      getElec() {
        queryElec().then(response => {
          this.tableData2 = response.rows;
        });
      },
      getBrowse() {
        queryBrowse().then(response => {
          this.tableData3 = response.rows;
          // const _this = this;
          // response.rows.map((item, index) => {
          //   let indexs = (index + 1).toString();
          //   if (index.toString().length < 3) {
          //     for (let i = 0; i < 3 - index.toString().length; i++) {
          //       indexs = '0' + indexs;
          //     }
          //   }
          //   item.code = indexs
          // })
        });
      },
      getBorrow() {
        queryBorrow().then(response => {
          this.tableData4 = response.rows;
        });
      },
      getAsk(){
        queryAskApplic().then(response => {
          this.tableData5 = response.rows;
        });
      },
      //临时申请
      temApplic() {
        this.$router.push({path: "/checkBorrowApprove/browse"});
      },
      //电子档案
      elecApplic() {
        this.$router.push({path: "/checkBorrowApprove/electronic"});
      },
      //查阅申请
      browseApplic() {
        this.$router.push({path: "/checkBorrowApprove/physical"});
      },
      //借阅申请
      borrowApplic() {
        this.$router.push({path: "/checkBorrowApprove/physicalBorrowApproval"});
      },
      //材料催缴
      askApplic() {
        this.$router.push({path: "/usually/collection"});
      }
    }
  };
</script>
<style lang="scss" scoped>
  .text {
    font-size: 18px;
    text-align:center;
    line-height: 300%;
  }

  /*.item {*/
  /*  margin-bottom: 18px;*/
  /*}*/

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    /*width: 480px;*/
    height: 120px;
    margin-top: 15px;
  }
  .look{
    text-decoration:underline;
    color: blue;
  }
</style>
