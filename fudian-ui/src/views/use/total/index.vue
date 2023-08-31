<template>
 <div class="use-total">
   <el-row :gutter="32" style="margin: 0 auto;margin-top: 16px">
     <el-col :xs="24" :sm="24" :lg="12">
       <div class="chart-wrapper">
         <el-tabs
           id="elTabs"
           :tab-position="tabPosition"
           @tab-click="tabClick"
           width="100%"
           v-model="oneActiveName"
         >
           <el-tab-pane label="干部状态统计"  name="one">
             <one-chart />
           </el-tab-pane>
         </el-tabs>

       </div>
     </el-col>
     <el-col :xs="24" :sm="24" :lg="12">
       <div class="chart-wrapper">
         <el-tabs
           id="elTabs2"
           v-model="twoActiveName"
           :tab-position="tabPosition"
           @tab-click="tabClick"
           width="100%"

         >
           <el-tab-pane label="实体档案查阅统计"  name="one">
             <two-one-chart ref="twoonechart"/>
           </el-tab-pane>
           <el-tab-pane label="实体档案借阅统计" name="tow">
             <two-two-chart ref="twotwochart"/>
           </el-tab-pane>
           <el-tab-pane label="电子档案查阅统计" name="three">
             <two-three-chart ref="twothreechart"/>
           </el-tab-pane>
         </el-tabs>

       </div>


     </el-col>
   </el-row>
   <el-row :gutter="32" style="margin: 0 auto">
     <el-col :xs="24" :sm="24" :lg="12">
       <div class="chart-wrapper">
         <el-tabs
           id="elTabs3"
           :tab-position="tabPosition"
           @tab-click="tabClick"
           width="100%"
           v-model="threeActiveName"
         >

           <el-tab-pane label="库房在库档案统计" name="one">
             <five-chart/>
           </el-tab-pane>
         </el-tabs>
       </div>
     </el-col>
     <el-col :xs="24" :sm="24" :lg="12">
       <div class="chart-wrapper">
         <el-tabs
           id="elTabs4"
           :tab-position="tabPosition"
           @tab-click="tabClick"
           width="100%"
           v-model="fourActiveName"
         >
           <el-tab-pane label="材料接收统计" name="one">
             <four-chart />
           </el-tab-pane>
         </el-tabs>
       </div>
     </el-col>
   </el-row>
   <el-row :gutter="32" style="margin: 0 auto">
     <el-col :xs="24" :sm="24" :lg="24">
       <div class="chart-wrapper">
         <el-tabs
           id="elTabs5"
           :tab-position="tabPosition"
           @tab-click="tabClick"
           width="100%"
           v-model="fiveActiveName"
         >
           <el-tab-pane label="转入转出统计" name="one">
             <three-chart />
           </el-tab-pane>
         </el-tabs>
       </div>
     </el-col>
<!--     <el-col :xs="24" :sm="24" :lg="12">-->

<!--     </el-col>-->
   </el-row>
 </div>

</template>

<script>
  import oneChart from './oneChart'
  import twoOneChart from './twoOneChart'
  import twoTwoChart from './twoTwoChart'
  import twoThreeChart from './twoThreeChart'
  import threeChart from './threeChart'
  import fourChart from './fourChart'
  import fiveChart from './fiveChart'


  export default {
    components: {
      oneChart,
      twoOneChart,
      twoTwoChart,
      twoThreeChart,
      threeChart,
      fourChart,
      fiveChart
    },
    data() {
      return {
        twoActiveName:'one',
        oneActiveName:'one',
        tabPosition: 'top',
        threeActiveName:'one',
        fourActiveName:'one',
        fiveActiveName:'one'
      }
    },
    mounted() {
    },
    methods: {
      tabClick(tab, event){
        let _this = this;
        setTimeout(function(){
          console.log(123)
          if(tab.label === '实体档案查阅统计'){
            console.log(_this.$refs.twoonechart.chart.resize());
          }else if(tab.label === '实体档案借阅统计'){
            console.log(_this.$refs.twotwochart.chart.resize());
          }else if(tab.label === '电子档案查阅统计'){
            console.log(_this.$refs.twothreechart.chart.resize());
          }
        },50)

      },
    }
  };

</script>
<style lang="scss" scoped>
  .use-total{
    background: #f0f2f5;
    overflow: auto;
    height: calc(100vh - 86px);
  }
  .col{
    text-align: center;
    width: 45%;
    margin-left:4%;
    height:100%;
    margin-top:5%;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
  }
  .charts {
    width: 100%;
    height: 100%;
  }
  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
  /deep/ .el-tabs__nav .is-top{
    /*float: ;*/

  }
  /deep/ .el-tabs__nav-wrap::after{



    height:1px;

  }
</style>
