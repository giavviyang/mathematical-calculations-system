<template>
  <el-dialog title="档案新增" :visible.sync="dialogFormVisible1">
<!--    <div style="width: 700px;height:350px;margin: 0 auto;">-->
<!--      <div style="float: left;width: 49%;height: 95%;border-right: rgba(31,45,61,0.14) 1px solid">-->
<!--        <div style="height:10%;margin-right:15px;border-bottom: rgba(31,45,61,0.14) 1px solid">选择干部</div>-->

<!--        <div style="width:49%;height:90%;float: left">-->
<!--          <el-scrollbar style="height: 100%;">-->
<!--            <el-tree-->
<!--              @node-click="flushA01"-->
<!--              :expand-on-click-node="false"-->
<!--              style="height:100%;margin-top: 20px;margin-left: 15px;"-->
<!--              ref="deptTree"-->
<!--              :data="deptData"-->
<!--              node-key="id"-->
<!--              highlight-current-->
<!--              :props="deptProps" />-->
<!--          </el-scrollbar>-->
<!--        </div>-->
<!--        <div style="width:49%;height:90%; float: right;border-left: rgba(31,45,61,0.14) 1px solid">-->
<!--          <el-scrollbar style="height: 100%;">-->
<!--            <el-tree-->
<!--              style="height:100%;margin-top: 20px;margin-left: 15px;"-->
<!--              @check="a01NodeClick"-->
<!--              ref="a01Node"-->
<!--              :data="a01Data"-->
<!--              show-checkbox-->
<!--              node-key="id"-->
<!--              highlight-current-->
<!--              :props="a01Props" />-->
<!--          </el-scrollbar>-->
<!--        </div>-->

<!--      </div>-->

<!--      <div style="float: left;width: 49%;height: 95%;">-->
<!--        <div style="height:10%;margin-left:15px;border-bottom: rgba(31,45,61,0.14) 1px solid">选择位置</div>-->
<!--        <el-scrollbar style="height: 90%;">-->
<!--          <el-tree-->
<!--            style="height:90%;width:100%;margin-top: 20px;margin-left: 15px;"-->
<!--            ref="tree3"-->
<!--            :expand-on-click-node="false"-->
<!--            @node-click="addA01TreeNodeClick"-->
<!--            lazy-->
<!--            :load="loadNode"-->
<!--            :data="data"-->
<!--            node-key="id"-->
<!--            highlight-current-->
<!--            :props="defaultProps" />-->
<!--        </el-scrollbar>-->
<!--      </div>-->
<!--    </div>-->
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dialogFormVisible1 = false">取消</el-button>
      <el-button type="primary" @click="dialogFormVisible1 = false">保存</el-button>
    </div>

  </el-dialog>
</template>

<script>
  // import { Notification, MessageBox } from 'element-ui'
  // import {queryA01,insertA01,downRoom,updateA01Position} from '@/api/room/arch'
  // import {queryRoomStruct} from '@/api/room/struct'
  // import { queryRoomList}  from "@/api/room/room";


  export default {
    props:{
      dialogFormVisible1:Boolean
    },
    data() {
      return {
        adddialogFormVisible : false
      }
    },

    methods: {
    }
  }
</script>

<style lang="scss" scoped>
  .el-dialog__body {
    height: 500px;
    display: block;
  }
  .el-input__inner {
    width: 250px;
  }
  .el-form-item__content {
    width: 250px;
  }
  .el-input {
    width: 250px;
  }
  .inputW {
    width: 250px;
  }
  .el-form--inline .el-form-item {
    margin:0px;
  }
</style>
