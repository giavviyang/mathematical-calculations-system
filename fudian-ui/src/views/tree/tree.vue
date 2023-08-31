<template>
  <div>
    <selectTree
    :testData="reportData"
    :selectSTyle="selectSTyle"
    :treeStyle="treeStyle"
    :isdisabled="isdisabled"
    @handleNode="handleNodeClick"
    @obtainFocus="obtainFocus"
    @loseBlur="loseBlur"
    :defaultProps="defaultProps"
    />
  </div>
</template>
<script>
  import selectTree from '../components/tree/tree.vue'
  export default {
    components:{
      selectTree
    },
    data() {
      return {
        isdisabled:false,
        selectSTyle:{
          width:"560px"
        },
        treeStyle:{
          width:"560px",
          height:"200px"
        },
         defaultProps: {
                  children: 'children',
                  label: 'label'
                },
        reportData:[
          {
            id:1,
            label:'一级部门',
            children:[
              {
                id:2,
                label:'二级部门',
              }
            ]
          }
        ]
      }
    },
    methods:{
      handleNodeClick(item){
        console.log(item)
      },
      // 失去焦点
      loseBlur(item){
        console.log(item)
      },
      // 获取焦点
      obtainFocus(item){
        console.log(item)
      }
    }
  }
</script>

<style>
</style>
