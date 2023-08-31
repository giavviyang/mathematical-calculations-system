<template>

  <el-card style="width: 100%;padding: 10px;" :body-style="bodystyle">
    <div slot="header">
      <span>待办事项</span>
           <!-- <el-button style="float: right; padding: 3px 0" type="text">更多</el-button> -->
    </div>
    <el-table :data="list" style="width: 100%;"  :show-header="false"   >
      <el-table-column label="" :show-overflow-tooltip="true" width="100%" >
        <template slot-scope="scope">
          ○{{ scope.row.documentType }}
        </template>
        </el-table-column>
      <el-table-column label="模板名称" :show-overflow-tooltip="true" align="left" >
        <template slot-scope="scope">
          {{ scope.row.documentName }}
        </template>
      </el-table-column>


      <!-- <el-table-column
        :fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row.documentId)" type="text" size="small">开始处理</el-button>
        </template>
      </el-table-column> -->


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
            documentType:'临时申请',
            documentName: '当前有临时申请100条',
            date:'2020-01-01'
          },
          {
            documentId: '2',
            documentType:'电子档案申请',
            documentName: '电子档案审批任务30个',
            date:'2020-01-01'
          },
          {
            documentId: '4',
            documentType:'查阅申请',
            documentName: '需您处理查阅申请200个',
            date:'2020-01-01'

          },
          {
            documentId: '5',
            documentType:'借阅申请',
            documentName: '需您审批借阅申请10个',
            date:'2020-01-01'
          },
          {
            documentId: '6',
            documentType:'材料催缴',
            documentName: '您当前需要处理催缴事宜有7条',
            date:'2020-01-01'
          },
          // {
          //   documentId: '4',
          //   documentName: '干部人事档案目录打印通知',
          //   date:'2020-01-01'

          // },
          // {
          //   documentId: '1',
          //   documentName: '系统停机升级公告',
          //   date:'2020-01-01'
          // },
          // {
          //   documentId: '2',
          //   documentName: '关于×××的通知',
          //   date:'2020-01-01'
          // },
          // {
          //   documentId: '4',
          //   documentName: '干部人事档案目录打印通知',
          //   date:'2020-01-01'

          // },
          // {
          //   documentId: '1',
          //   documentName: '系统停机升级公告',
          //   date:'2020-01-01'
          // },
          // {
          //   documentId: '1',
          //   documentName: '系统停机升级公告',
          //   date:'2020-01-01'
          // }
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
