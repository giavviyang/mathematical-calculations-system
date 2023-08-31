<template>
  <el-form-item label="字典标签" label-width="68px" size="mini" v-model="form">
    <el-select v-model="form" placeholder="请选择字典标签" clearable class="inputW"  ref="selectReport">
      <el-option :value="form.dictValueid" :label="form.dictLabel" style="height:200px;width:200px;overflow:auto;background-color:#fff">
        <el-tree
          :data="treeData"
          :props="TreeDefaultProps"
          @node-click="handleSelectClick"
        ></el-tree>
      </el-option>
    </el-select>
  </el-form-item>
</template>
<script>
    import {getTreeByDictType} from "../../api/system/dict/type";
    export default {
      watch:{},
      data(){
          return{
            //传参数dictType，
            form:{
              dictLabel: '',
              dictValueid:'',
            },
            TreeDefaultProps: {
              label: "dictLabel",
              children: "children"
            },
            treeData:[],
          };
      },
      props:{
        transForm:{
          type:Object,
          required:true
        },
      },
      methods:{
        handleSelectClick(data, node, component){
          this.form.dictLabel=data.dictLabel
          this.form.dictValueid=data.dictValueid
          this.$refs.selectReport.blur()

          //往父组件中传选中的数据
          this.$emit('child-event',this.form)
        },
        getTree(){
          getTreeByDictType(this.transForm)
            .then(response=>{
              if (response.code==200){
                if (response.data===1){
                  this.treeData=[]
                }else {
                  this.treeData=response.data
                }
              }
            });
        },
      },
      created() {
        this.getTree();
      }
    }
</script>

<style scoped>
  .inputW{
    width: 200px;
  }
</style>
