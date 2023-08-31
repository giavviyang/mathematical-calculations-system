<!--<template>-->
<!--  <div class="app-search">-->
<!--    <el-form :inline="true" class="search-form" style="background:rgba(204, 204, 204, 0.18);margin-bottom: 10px;padding: 15px;">-->
<!--     <el-form-item label="干部名称">-->
<!--              <el-input placeholder="请输入内容" size="mini"></el-input>-->
<!--            </el-form-item>-->
<!--      &lt;!&ndash; <el-form-item label="转出时间">-->
<!--        <el-input v-model="formInline.user" suffix-icon="el-icon-date" placeholder="请输入内容"></el-input>-->
<!--      </el-form-item> &ndash;&gt;-->
<!--      <el-form-item label="转出时间" size="mini">-->
<!--    <el-col :span="11">-->
<!--      <el-date-picker type="date" placeholder="选择日期" ></el-date-picker>-->
<!--    </el-col>-->
<!--    <el-col class="line" :span="2">-</el-col>-->
<!--    <el-col :span="11">-->
<!--      <el-time-picker type="date" placeholder="选择时间" ></el-time-picker>-->
<!--    </el-col>-->
<!--  </el-form-item>-->
<!--     <el-form-item label="转递单号" style="margin-left:15px">-->
<!--              <el-input placeholder="请输入内容" size="mini"></el-input>-->
<!--            </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" size="mini" icon="el-icon-search" @click="onSubmit">搜索</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button size="mini" icon="el-icon-refresh" @click="onSubmit">重置</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" size="mini" icon="el-icon-zoom-in" @click="searchDialog()">高级检索</el-button>-->
<!--        &lt;!&ndash; <el-button type="primary" icon="el-icon-search" @click="searchDialog()">高级检索</el-button> &ndash;&gt;-->
<!--      </el-form-item>-->
<!--    </el-form>-->

<!--      &lt;!&ndash; 高级检索对话框 &ndash;&gt;-->
<!--    <el-dialog title="高级检索2" :visible.sync="dialogVisible" :before-close="handleClose" width="80%">-->
<!--      &lt;!&ndash; //查询条件 &ndash;&gt;-->
<!--      <el-form :inline="true" :model="searchTableForm">-->
<!--        <el-form-item label="字段名" prop="key">-->
<!--          <el-select v-model="searchTableForm.key" placeholder="请选择">-->
<!--&lt;!&ndash;      <el-option label="区域一" value="shanghai"></el-option>&ndash;&gt;-->
<!--&lt;!&ndash;      <el-option label="区域二" value="beijing"></el-option>&ndash;&gt;-->
<!--            <el-option v-for="item in keyData" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />-->
<!--    </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="比较符" prop="oper">-->
<!--         <el-select v-model="searchTableForm.oper" placeholder="请选择">-->
<!--           <el-option label="等于" value="EQ" />-->
<!--           <el-option label="不等于" value="NE" />-->
<!--           <el-option label="小于" value="LT" />-->
<!--           <el-option label="大于" value="GT" />-->
<!--           <el-option label="小于等于" value="LE" />-->
<!--           <el-option label="大于等于" value="GE" />-->
<!--           <el-option label="包含" value="LIKE" />-->
<!--           <el-option label="不包含" value="NOTLIKE" />-->
<!--    </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="字段值" prop="value">-->
<!--          <el-input v-model="searchTableForm.value" placeholder="请输入内容"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item>-->
<!--          <el-button type="primary" @click="addSearchTableForm">添加</el-button>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      &lt;!&ndash; 表格展示条件 &ndash;&gt;-->
<!--      <el-table :data="tableData" border style="width: 100%">-->
<!--&lt;!&ndash;        <el-table-column type="index" label="序号" width="50">&ndash;&gt;-->
<!--&lt;!&ndash;        </el-table-column>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column prop="name" label="字段名" style="width: 33%">&ndash;&gt;-->
<!--&lt;!&ndash;        </el-table-column>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column prop="address" label="比较符" style="width: 33%">&ndash;&gt;-->
<!--&lt;!&ndash;        </el-table-column>&ndash;&gt;-->
<!--&lt;!&ndash;        <el-table-column prop="address" label="字段值" style="width: 33%">&ndash;&gt;-->
<!--&lt;!&ndash;        </el-table-column>&ndash;&gt;-->
<!--        <el-table-column prop="key" label="字段名" style="width: 33%" />-->
<!--        <el-table-column prop="oper" label="比较符" style="width: 33%" />-->
<!--        <el-table-column prop="value" label="字段值" style="width: 33%" />-->
<!--      </el-table>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>-->
<!--      </span>-->
<!--    </el-dialog>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->

<!--  data() {-->
<!--    return {-->
<!--        keyData:[-->
<!--            { dictCode: 'a0101', fullName: '姓名' },-->
<!--            { dictCode: 'a0102', fullName: '姓名拼音' },-->
<!--            { dictCode: 'a0104', fullName: '性别' },-->
<!--            { dictCode: 'a0107', fullName: '出生日期' },-->
<!--            { dictCode: 'a0111a', fullName: '籍贯名称' },-->
<!--        ],-->
<!--      dialogVisible:false,-->
<!--      formInline: {-->
<!--        user: "",-->
<!--        region: ""-->
<!--      },-->
<!--      // 高级搜索 form-->
<!--      searchTableForm: {},-->

<!--    }-->
<!--  },-->
<!--  methods:{-->
<!--    searchDialog(){-->
<!--      this.dialogVisible = true-->
<!--    }-->
<!--  }-->
<!--};-->
<!--</script>-->

<!--<style lang="scss" scoped>-->
<!--.app-search {-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--  padding-left: 20px;-->
<!--  padding-top: 15px;-->
<!--  padding-right: 20px;-->
<!--}-->
<!--.el-form&#45;&#45;inline .el-form-item {-->
<!--margin:0px;-->
<!--}-->
<!--/deep/.el-form-item&#45;&#45;mini .el-form-item__label{-->
<!--line-height: 32px;-->
<!--}-->
<!--/deep/.el-input&#45;&#45;prefix .el-input__inner{-->
<!--  margin-top:5px;-->
<!--}-->
<!--/deep/.el-form-item&#45;&#45;mini .el-form-item__label {-->
<!--    line-height: 37px;-->
<!--}-->
<!--</style>-->
<template>
  <div class="app-search">
    <el-form :inline="true" class="search-form">
      <el-form-item label="干部名称">
        <el-input v-model="formInline.user" placeholder="请输入内容"></el-input>
      </el-form-item>
      <!-- <el-form-item label="转出时间">
        <el-input v-model="formInline.user" suffix-icon="el-icon-date" placeholder="请输入内容"></el-input>
      </el-form-item> -->
      <el-form-item label="转出时间">
    <el-col :span="11">
      <el-date-picker type="date" placeholder="选择日期" ></el-date-picker>
    </el-col>
    <el-col class="line" :span="2">-</el-col>
    <el-col :span="11">
      <el-time-picker type="date" placeholder="选择时间" ></el-time-picker>
    </el-col>
  </el-form-item>
      <el-form-item label="转递单号">
        <el-input v-model="formInline.user" placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="warning" @click="onSubmit">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchDialog()">高级检索</el-button>
      </el-form-item>
    </el-form>

      <!-- 高级检索对话框 -->
    <el-dialog title="高级检索" :visible.sync="highdialogVisible" :before-close="handleClose" width="80%">
      <!-- //查询条件 -->
      <el-form :inline="true" class="" :model="searchTableForm">
        <el-form-item label="字段名" prop="key">
          <el-select v-model="searchTableForm.key" placeholder="请选择">
            <el-option v-for="item in keyData" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
    </el-select>
        </el-form-item>
        <el-form-item label="比较符" prop="oper">
         <el-select v-model="searchTableForm.oper" placeholder="请选择">
           <el-option label="等于" value="EQ" />
           <el-option label="不等于" value="NE" />
           <el-option label="小于" value="LT" />
           <el-option label="大于" value="GT" />
           <el-option label="小于等于" value="LE" />
           <el-option label="大于等于" value="GE" />
           <el-option label="包含" value="LIKE" />
           <el-option label="不包含" value="NOTLIKE" />
    </el-select>
        </el-form-item>
        <el-form-item label="字段值" prop="value">
          <el-input v-model="searchTableForm.value" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSearchTableForm">添加</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格展示条件 -->
      <el-table :data="tableData" border style="width: 100%">
<!--        <el-table-column type="index" label="序号" width="50">-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="name" label="字段名" style="width: 33%">-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="address" label="比较符" style="width: 33%">-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="address" label="字段值" style="width: 33%">-->
<!--        </el-table-column>-->
        <el-table-column prop="key" label="字段名" style="width: 33%" />
        <el-table-column prop="oper" label="比较符" style="width: 33%" />
        <el-table-column prop="value" label="字段值" style="width: 33%" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="highdialogVisible = false">取 消</el-button>
<!--        <el-button type="primary" @click="determineSave" dialogVisible = false">确 定</el-button>-->
        <el-button type="primary" @click="determineSave">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {

  data() {
    return {
        // 高级搜索 form
        searchTableForm: {},
        //搜索条件值
        keyData:[
            { dictCode: 'e01z701', fullName: '转递日期' },
            { dictCode: 'e01z704a', fullName: '转往单位' },
            { dictCode: 'personNum', fullName: '转出人数' },
            { dictCode: 'e01z717', fullName: '经办人' },
            // { dictCode: 'a0111a', fullName: '有无回执' },
            { dictCode: 'e01z727', fullName: '回执日期' },
            // { dictCode: 'a0114a', fullName: '转递单号' },
            { dictCode: 'ramark', fullName: '备注' },
        ],
      tableData:[],
      highdialogVisible:false,
      formInline: {
        user: "",
        region: ""
      },
    }
  },
  methods:{
    searchDialog(){
      this.highdialogVisible = true
    },
    onSubmit(){},
    handleClose(){},
  //检索条件添加
      addSearchTableForm(){
          this.tableData.push(this.searchTableForm)
          console.log(this.tableData)
      },
  //高级检索确定检索功能
      determineSave(){
          const _this = this
          const data = {
              conditionsJsonArray: JSON.stringify(this.tableData),
              pageNum: 1,
              pageSize: 20
          }
          queryListByConditionList(data)
              .then(response => {
                  _this.tableData = response.rows
                  this.highdialogVisible = false
              })
      }
  }
};
</script>

<style lang="scss" scoped>
.app-search {
  width: 100%;
  height: 100%;
  padding-left: 20px;
  padding-top: 15px;
}
</style>
