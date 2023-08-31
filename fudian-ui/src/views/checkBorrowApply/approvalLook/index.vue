<template>
  <div class="app-approvalLook">
    <div class="table" v-if="ok">
      <el-table ref="tableRefs"
                :cell-style="cellStyle"
                :data="tableData"
                row-key="id" border size="mini"
                :header-cell-style="headClass"
                tooltip-effect="light"
                default-expand-all
                :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
                element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">
<!--        <el-table-column type="selection"></el-table-column>-->
        <el-table-column label="序号" align="left" prop="e01z104" width="100" />
        <el-table-column label="材料名称" header-align="center" prop="e01z111a" show-overflow-tooltip width="350">
        </el-table-column>
        <el-table-column label="材料形成时间" align="center">
          <el-table-column label="年" prop="year" align="center" width="70px">
          </el-table-column>
          <el-table-column label="月" prop="month" align="center" width="70px">
          </el-table-column>
          <el-table-column label="日" prop="day" align="center" width="70px">
          </el-table-column>
        </el-table-column>
        <el-table-column label="页数" prop="e01z114" align="center" width="70px">
        </el-table-column>
        <el-table-column label="备注" prop="e01z121" align="center" show-overflow-tooltip>
        </el-table-column>
<!--        <el-table-column label="电子文件" prop="filed" align="center" >-->
<!--        </el-table-column>-->
        <el-table-column fixed="right" label="操作" align="center" width="130px">
          <template  slot-scope="scope">
            <el-button v-if="scope.row.type === 3" @click="look(scope.row)" type="text" size="small">查看电子文件</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="width: 100%;height:500px" v-if="no">
      <div style="text-align: center;margin-top:10%;height: 100%">
        <span style="color:red;font-size: 30px">查阅时间已到，请重新申请！</span>
      </div>
    </div>
  </div>

</template>
<script>
import { queryA01List } from '@/api/usual/transfer'
import { queryDictionaryByType,queryE01z1TreeGrid} from '@/api/usual/receive'
import { getE01z1TreeByRelaId } from '@/api/usual/provisonalInspect'
import { queryFileList,deleteFile } from '@/api/usual/file'
export default {
  name:'查看电子档案',
  data() {
    return {
      tableData:[],
      // cellStyle:'',
      a01Id :this.$route.params.a01Id,
      a0101:this.$route.params.a0101,
      id:this.$route.params.e01zcRelaId,
      uploadUrl:'',
      imgFileVisable:false,
      srcList:[],
      imgDatas:[],
      imgClass:'width: 33%;height:70%;float: left;padding:20px;margin-top: 20px;margin-bottom: 30px;',
      ok :true,
      no:false,

    }
  },
  mounted() {

  },
  created(){
    let _this = this;
    let loadIng = function(time){
      setTimeout(()=>{
        let ntime = new Date().getTime();
        if(ntime>time){
          //展示遮罩
          _this.imgFileVisable = false;
          _this.ok = false;
          _this.no = true;
        }else{
          loadIng(time);
        }
      },10000);

    }
    getE01z1TreeByRelaId(this.id).then(response=>{
      _this.tableData = response.data.data;

      let endDate = this.moment(response.data.e01zc.endDate).valueOf();
      let time = new Date(endDate).getTime();
      // let time = new Date(response.data.e01zc.endDate).getTime();
      if(new Date().getTime()>time){
        _this.imgFileVisable = false;
        _this.ok = false;
        _this.no = true;
      }else{
        loadIng(time);
      }
    })
  },
  methods: {
    getChildrenByE01z101(){
      let datas = [];
      this.tableData.map(item=>{
        if(item.id==4){
          item.children.map(chil=>{
            if(typeof chil.children!='undefined'){
              datas.push(...chil.children)
            }
          })
        } else if(item.id==9){
          item.children.map(chil=>{
            if(typeof chil.children!='undefined'){
              datas.push(...chil.children)
            }
          })
        }else{
          if(typeof item.children!='undefined'){
            datas.push(...item.children)
          }

        }

      })
      return datas;
    },
    queryImgDatasByBindId(id){
      queryFileList(id).then(response=>{
        this.srcList = [];
        response.data.map(item=>{
          item.url = "/fileStorage/downFileByCheckId?fileId=" + item.id;
          item.imgName = this.e01z1Form.e01z101 + "-" + this.e01z1Form.e01z104 + "-" + item.fileIndex;
          this.srcList.push(item.url);
        })
        this.imgDatas = response.data;
      })
    },
    look(row){
      // this.uploadUrl = '/person/checkPageById?id=' + row.id;
      // this.queryImgDatasByBindId(row.id);
      // this.e01z1Form = row;
      // this.lookTitle = row.e01z111a;
      // this.imgFileVisable = true;
      console.log(1211)
      console.info(this)

      let params = {
        fileAttribute: row,
        AllFile:this.tableData,
      }
      this.$router.push({name: "临时查借阅浏览页面"})
      sessionStorage.setItem('materialInformation', JSON.stringify(params));

      // this.$router.push({
      //   name: "临时查借阅浏览页面",
      //   query: {
      //     fileAttribute: row,
      //     AllFile:this.tableData,
      //     // id:this.id
      //   }
      // })
    },
    cellStyle({ row, column, rowIndex, columnIndex }) {
       if(row.remark == 'catalog'){
         return 'fontSize:14px;background:#F5F7FA;color:red';
       }else{
         return "";
       }
    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;font-size:12px";
    },
  }
}
</script>

<style lang="scss" scoped>
  .app-approvalLook{
    overflow: auto;
    height: calc(100vh - 86px);
  }
  /deep/.el-scrollbar__wrap
  {
    overflow-x: hidden;
  }

  .dialog-style {
    /deep/.el-dialog__body {
      padding: 10px;
    }
    /deep/.el-dialog__header {
      background: rgba(204, 204, 204, 0.18);
      padding: 10px;
      height: 100px;
    }
    /deep/.el-dialog__title {
      font-weight: 500;
      font-size: 15px
    }
    /deep/.el-dialog__headerbtn {
      top: 13px;
    }

    /deep/.el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      /*height:20px;*/
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }
  }
  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
  }
  .dialog-basic {
    margin-left: 10px;
    .form-basic {
      margin-bottom: 15px;
      line-height: 30px;
      border-bottom: 1px solid #ccc;
      margin-left: 15px;
      margin-right: 15px;
    }
    .form-supplement {
      margin-bottom: 15px;
      line-height: 30px;
      border-bottom: 1px solid #ccc;
      margin-left: 15px;
      margin-right: 15px;
    }
    /deep/.el-select>.el-input{
      width: 163px !important;
    }
  }
</style>
