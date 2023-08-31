<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="peoples" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            累计访问
          </div>
          <count-to :start-val="0" :end-val="countMax" :duration="2600" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <!--<el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('messages')">
        <div class="card-panel-icon-wrapper icon-room3d">
          <svg-icon icon-class="room3d" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            库房存储
          </div>
          <count-to :start-val="0" :end-val="storageMax" :duration="3000" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('purchases')">
        <div class="card-panel-icon-wrapper icon-borrows">
          <svg-icon icon-class="borrows" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            累计借阅
          </div>
          <count-to :start-val="0" :end-val="countFileMax" :duration="3200" class="card-panel-num"/>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('shoppings')">
        <div class="card-panel-icon-wrapper icon-chayuerenshu">
          <svg-icon icon-class="chayuerenshu" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            累计查阅
          </div>
          <count-to :start-val="0" :end-val="countSeeMax" :duration="3000" class="card-panel-num"/>
        </div>
      </div>
    </el-col>-->
  </el-row>
</template>

<script>
  import CountTo from 'vue-count-to'
  import {queryRoomList} from "@/api/room/room"
  import {getEntityArchiveCApplyRecord, getEntityArchiveAApplyRecord} from '@/api/usual/usage/borrow'
  import {getApprovals} from '@/api/usual/usage/electronic-access-approval'
  import {queryAllLoginLog} from "@/api/log/loginlog";
  export default {
    components: {
      CountTo
    },
    data() {
      return {
        countMax: 0,
        storageMax: 0,
        countFileMax: 0,
        countSeeMax: 0,
        queryForm: {
          pageNum: 1,
          pageSize: 999999
        }
      }
    },
    created() {
      // this.getRoomList();
      // this.getFileLendCount();
      // this.getSeeLs();
      this.getCountLog();
    },
    methods: {
      handleSetLineChartData(type) {
        this.$emit('handleSetLineChartData', type)
      },
      getRoomList() {
        queryRoomList(this.queryForm).then(response => {
          let num = 0;
          response.rows.map((item, index) => {
            num += parseInt(item.curNum)
          })
          this.storageMax = num;
        })

      },
      getFileLendCount() {
        let params = {
          pageNum: 1,
          pageSize: 1,
          status: 1
        }
        getEntityArchiveAApplyRecord(params).then(response => {
          this.countFileMax = response.total;
        })
      },
      getSeeLs() {
        let _this = this;
        let num = 0;
        let p1 = {
          type: 1,
          target: 2,
          approval: 1,
          pageNum: 1,
          pageSize: 1
        }
        getApprovals(p1).then(function (response) {
          console.log(response.total)
          num += parseInt(response.total)
          p1.target = 1;
          getApprovals(p1).then(function (response) {
            console.log(response.total)
            num += parseInt(response.total)
            let p2 = {
              pageNum: 1,
              pageSize: 1,
              status: 1
            }
            getEntityArchiveCApplyRecord(p2).then(response => {
              console.log(response.total)
              num += parseInt(response.total)
              console.log(num)
              _this.countSeeMax = num;
            })
          })
        })
      },
      getCountLog(){
        let params = {
          pageNum: 1,
          pageSize: 1
        }
        queryAllLoginLog(params).then(response => {
          this.countMax = response.total;
        })
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

        .icon-borrows {
          background: #f4516c;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-chayuerenshu {
          background: #36a3f7;
        }

        .icon-room3d {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }

        .icon-shopping {
          background: #34bfa3
        }
      }

      .icon-chayuerenshu {
        color: #36a3f7;
      }

      .icon-borrows {
        color: #f4516c;
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-room3d {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .icon-shopping {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
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

  @media (max-width: 550px) {
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
