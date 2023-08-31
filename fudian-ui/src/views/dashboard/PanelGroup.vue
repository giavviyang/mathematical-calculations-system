<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
<!--      @click="handleSetLineChartData('newVisitis')"-->
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            档案系统访问次数
<!--            系统访问次数-->
          </div>
          <count-to :start-val="0" :end-val="1921" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-chayuerenshu">
          <svg-icon icon-class="chayuerenshu" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            外来查阅次数总数
<!--            查阅次数总数-->
          </div>
          <count-to :start-val="0" :end-val="541" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-ganbu">
          <svg-icon icon-class="ganbu" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            干部档案管理总数
<!--            档案管理总数-->
          </div>
          <count-to :start-val="0" :end-val="687" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" >
        <div class="card-panel-icon-wrapper icon-zhaopian">
          <svg-icon icon-class="zhaopian" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            档案电子照片总数
<!--            电子照片总数-->
          </div>
          <count-to :start-val="0" :end-val="601289" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'

export default {
  components: {
    CountTo
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    // display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      /*.icon-message {*/
      /*  background: #36a3f7;*/
      /*}*/

      .icon-chayuerenshu {
        background: #36a3f7;
      }

      /*.icon-money {*/
      /*  background: #f4516c;*/
      /*}*/

      .icon-ganbu {
        background: #f4516c;
      }

      /*.icon-shopping {*/
      /*  background: #34bfa3*/
      /*}*/

      .icon-zhaopian {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    /*.icon-message {*/
    /*  color: #36a3f7;*/
    /*}*/

    .icon-chayuerenshu {
      color: #36a3f7;
    }

    /*.icon-money {*/
    /*  color: #f4516c;*/
    /*}*/

    .icon-ganbu {
      color: #f4516c;
    }

    /*.icon-shopping {*/
    /*  color: #34bfa3*/
    /*}*/

    .icon-zhaopian {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      // float: left;
      // margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      // float: right;
      font-weight: bold;
      // margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
