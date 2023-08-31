<template>
  <div class="testTree" style="padding: 0;">
    <el-select
      :size="size"
      v-model="select.label"
      :disabled="isdisabled"
      :placeholder="placeholder"
      :style="selectStyle"
      class="el-select"
      @blur="loseBlur"
      @change="selectChange"
      :filterable="filterable"
      :filter-method="searchTree"
      @focus="obtainFocus"
      ref="selectReport">
      <el-option class="el-option tree tree-style"  :value="select.id" :label="select.label" :style="optionStyle">
        <div>
          <el-scrollbar :style="scrollbarStyle">
            <el-tree
              :data="datas"
              :default-expand-all="defaultExpandAll"
              :props="defaultProps"
              :load="loadNode"
              :lazy="lazy"
              :filter-node-method="treeNode"
              @node-click="handleNodeClick"
              ref="fudianCustomTree"
            ></el-tree>
          </el-scrollbar>
        </div>
      </el-option>
    </el-select>
  </div>
</template>
<script>
  export default {
    name:"testTree",
    //传递过来的参数
    props:{
      treeWidth:{
        type:Object
      },
      //tree型数据
      datas:{
        type:Array
      },
      //是否可以过滤
      filterable:{
        type:Boolean,
        default:false
      },
      //是否可编辑
      isdisabled:{
        type:Boolean,
        default:false,
      },
      //字段对应
      defaultProps:{
        type:Object
      },
      //是否能够选择父节点
      ischecked:{
        type:Boolean,
        default:false
      },
      //值为空时显示内容
      placeholder:{
        type:String,
        default:"请选择"
      },
      //控件尺寸
      size:{
        type:String,
      },
      //控件宽度
      width:{
        type:String,
        default:"100%"
      },
      //树高度
      height:{
        type:String,
        default:"150px"
      },
      //是否默认展开树节点
      defaultExpandAll:{
        type:Boolean,
        default:true
      },
      // 是否懒加载
      lazy:{
        type:Boolean,
      },
      value: {
        type: String,
      },
    },
    mounted() {
      this.$nextTick(() => {
        this.optionStyle.width=((this.$refs.selectReport.$el.offsetWidth-2)+"px")
      })
      window.onresize = () => {
        return (() => {
          this.optionStyle.width=((this.$refs.selectReport.$el.offsetWidth-2)+"px")
        })();
      };
    },
    data() {
      return {
        selectWidth:this.$refs.selectReport,
        select:{
          id:'',
          label:''
        },
        reportData:this.data,
        optionStyle:{
          width:"",
          height:this.height
        },
        selectStyle:{
          width:this.width,
        },
        scrollbarStyle:{
          margin:'2px',
          height:this.height,
          width:"100%"
        },
        isSelectTree:false,
      }
    },
    watch:{
      value:{
        immediate:true,
        handler:function(){
          this.initSelectLable();
        }
      },
    },
    methods:{
      //回显参数函数
      initSelectLable(){
        if(this.value!=null&&this.value!=""){
          let node=this.findTree(this.value);
          if(node!=null){
            this.select.id=this.value;
            this.select.label=node[this.getColumn("label")]
          }else{
            this.select.id=""
            this.select.label=""
          }
        }else{
          this.select.id=""
          this.select.label=""
        }
      },
      //动态赋值操作
      findTree(id){
        for(let n in this.datas){
          if(this.datas[n][this.getColumn("id")]==id){
            return this.datas[n];
          }
          if(this.datas[n][this.getColumn("children")]!=null
            &&this.datas[n][this.getColumn("children")].length>0){
            let node=this.findChidlrenTree(this.datas[n][this.getColumn("children")],id);
            if(node!=null){
              return node;
            }
          }
        }
        return null;
      },
      findChidlrenTree(list,id){
        for(let n in list){
          if(list[n][this.getColumn("id")]==id){
            return list[n];
          }
          if(list[n][this.getColumn("children")]!=null&&list[n][this.getColumn("children")].length>0){
            let node=this.findChidlrenTree(list[n][this.getColumn("children")],id);
            if(node!=null){
              return node;
            }
          }
        }
        return null;
      },

      //tree树字段对应children id label
      getColumn(column){
        if(this.defaultProps!=null){
          if(this.defaultProps[column]!=null&&this.defaultProps[column]!=""){
            return this.defaultProps[column]
          }
        }
        return column
      },

      handleNodeClick:function(node){
        this.isSelectTree=true;
        if(this.ischecked){
          this.select.id = node[this.getColumn("id")]
          this.select.label = node[this.getColumn("label")]
          this.$refs.selectReport.blur()
        }else if(!node.children){
          this.select.id = node[this.getColumn("id")]
          this.select.label = node[this.getColumn("label")]
          this.$refs.selectReport.blur()

        }
        this.$emit("input",node[this.getColumn("id")])
        this.$emit("handleNode",node)
      },
      selectChange(val){
        if(!this.isSelectTree){
          let node = this.findTree(val)
          this.select.id = node[this.getColumn("id")]
          this.select.label = node[this.getColumn("label")]
          this.$emit("handleNode",node)
          this.$emit("input",val)
        }

      },
      // 获取焦点
      obtainFocus(event){
        this.$emit("obtainFocus",event)
      },
      // 失去焦点
      loseBlur(event){
        this.isSelectTree=false;
        this.$emit("loseBlur",event)
      },
      // 异步获取
      loadNode(node, resolve){
        let nodes = {
          node:node,
          resolve:resolve
        }
        this.$emit("loadNode",nodes)
      },
      //过滤时使用的方法
      searchTree(val){
        this.$refs.fudianCustomTree.filter(val);
      },
      // 树节点筛选
      treeNode(value, data) {
        if (!value) return true;
        return data[this.getColumn("label")].indexOf(value) !== -1;
      },
    }
  }
</script>

<style scoped>
  .el-select-dropdown__item{
    margin: 0;
    padding: 0;
  }
  .el-option{
    min-height: 150px;
    max-height: 200px;
    background:#fff;
  }

  .el-select{
    background: #ffffff;
  }
  .el-scrollbar {
    height: 100%;
    overflow:hidden;
  }
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x:hidden;
  }
  /*/deep/ .el-tree > .el-tree-node {*/
  /*  min-width: 100%;*/
  /*  display:inline-block;*/
  /*  background: #ffffff;*/
  /*}*/
  /deep/ .el-tree > .el-tree-node__content{
    　　　　display: block!important;
  }

</style>
