<template>
<div class="app-wait">
<el-row>
  <el-col :span="8">
      <div class="table-wait">
          <el-table
          border
      :data="tableData"
      style="width: 100%">
      <el-table-column
      type="index"
        label="序号"
        width="55">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        style="width: 24%">
      </el-table-column>
      <el-table-column
        prop="date"
        label="材料名称"
        style="width: 24%">
      </el-table-column>
      <el-table-column
        prop="address"
        label="应扫页数"
        style="width: 24%">
      </el-table-column>
      <el-table-column
        prop="address"
        label="页数"
        style="width: 24%">
      </el-table-column>
    </el-table>

       <!-- 分页 -->
          <div class="page">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage4"
              :page-sizes="[100, 200, 300, 400]"
              :page-size="100"
              layout="total, sizes, prev, pager, next, jumper"
              :total="400"
            ></el-pagination>
            </div>
      </div>
      </el-col>
  <el-col :span="16">
      <!-- <div class="table-img"> -->
               <!-- 功能按钮 -->
        <div class="btn">
            <el-button type="primary" class="top-btn" >
              <i class="el-icon-download" ></i>导入
            </el-button>
            <el-button type="primary" class="top-btn">
              <i class="el-icon-download"></i>上传
            </el-button>
             <el-button type="primary" class="top-btn">
              <i class="el-icon-download"></i>所有电子文件
            </el-button>
             <el-button type="primary" class="top-btn">
              <i class="el-icon-download"></i>选择任务
            </el-button>
             <el-button type="primary" class="top-btn">
              <i class="el-icon-download"></i>上传进度
            </el-button>
             <el-button type="primary" class="top-btn">
              <i class="el-icon-download"></i>图像处理
            </el-button>
          </div>

     <el-row>
  <el-col :span="8">
      <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
  <el-col :span="8">
      <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
  <el-col :span="8">
       <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
    <el-col :span="8">
      <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
  <el-col :span="8">
      <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
  <el-col :span="8">
       <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
    <el-col :span="8">
      <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
  <el-col :span="8">
      <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
  <el-col :span="8">
       <div class="img">
          <img style=" width: 300px" src="../../../assets/404_images/404.png" alt="">
      </div>
  </el-col>
</el-row>
          
        

      </el-col>
</el-row>
</div>
</template>


<script>
export default {
      data() {
        return {
          tableData: [{
            date: '干部履历表',
            name: '王小虎',
            address: '1'
          }, {
            date: '干部履历表',
            name: '王小虎',
            address: '1'
          }, {
            date: '干部履历表',
            name: '王小虎',
            address: '1'
          }, {
            date: '干部履历表',
            name: '王小虎',
            address: '1'
          }]
        }
      }
    }
</script>

<style lang="scss" scoped>
.table-wait{
    width: 100%;
    min-height: calc(100vh - 100px);
    border:1px solid #e2e3e6;
}
.page {
  text-align: center;
}
.btn {
    padding-left: 10px;
    padding-top: 10px;
}
.top-btn {
    margin:5px;
    padding-bottom: 10px;
}
</style>