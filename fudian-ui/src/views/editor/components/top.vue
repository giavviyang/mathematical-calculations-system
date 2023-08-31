<template>

  <el-card style="width: 100%; padding: 5px;" :body-style="bodystyle">
    <div slot="header">
      <span>通知&公告</span>
        <el-button
              style="float: right; padding: 3px 0"
              type="text">更多</el-button>
    </div>
    <el-table :data="list" style="width: 100%;"  :show-header="false">
      <el-table-column label="模板名称"  :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          ○{{ scope.row.documentName }}
        </template>
      </el-table-column>

      <el-table-column label="时间" :fixed="right"  width="100px">
        <template slot-scope="scope">
          {{ scope.row.date }}
        </template>
      </el-table-column>


    </el-table>
  </el-card>
</template>

<script>
  export default {
    data() {
      return {
        bodystyle:{
          padding:'0'
        },
        list: null
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.list = [
          {
            documentId: '1',
            documentName: '系统停机升级公告',
            date:'2020-01-01'
          },
          {
            documentId: '2',
            documentName: '关于×××的通知',
            date:'2020-01-01'
          },
          {
            documentId: '4',
            documentName: '干部人事档案目录打印通知',
            date:'2020-01-01'

          },
          {
            documentId: '5',
            documentName: '档案整理通知',
            documentVersion: 'GB2019-1'
          },
          {
            documentId: '6',
            documentName: '员工“三龄一历”确认表',
            documentVersion: 'GB2019-1'
          },
          {
            documentId: '7',
            documentName: '干部人事档案审核整理责任卡',
            documentVersion: 'GB2019-1'
          },
          // {
          //   documentId: '8',
          //   documentName: '干部人事档案审核整理责任卡',
          //   documentVersion: 'GB2019-1'
          // },

        ]
      }
    }
  }
</script>


<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
