<template>
  <div class="app-inventory">
    <el-row>
      <el-col :span="4">
        <!-- 树结构 -->
        <div class="tree">
          <el-input placeholder="请输入内容" class="inp-select" />
          <el-tree
            ref="tree2"
            :data="data2"
            :props="defaultProps"
            :filter-node-method="filterNode"
            class="filter-tree"
            default-expand-all
          />
        </div>
      </el-col>
      <el-col :span="20">
        <div id="chartPieBox" class="chartStyle">饼图</div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //树结构数据
      data2: [
        {
          id: 1,
          label: "库房列表",
          children: [
            {
              id: 4,
              label: "库房a",
              children: [
                {
                  id: 9,
                  label: "1列"
                },
                {
                  id: 10,
                  label: "1节"
                },
                {
                  id: 10,
                  label: "1层"
                }
              ]
            },
            {
              id: 2,
              label: "库房b",
              children: [
                {
                  id: 5,
                  label: "1列"
                },
                {
                  id: 6,
                  label: "1节"
                },
                {
                  id: 6,
                  label: "1层"
                }
              ]
            },
            {
              id: 3,
              label: "库房3",
              children: [
                {
                  id: 7,
                  label: "1列"
                },
                {
                  id: 8,
                  label: "1节"
                },
                {
                  id: 8,
                  label: "1层"
                }
              ]
            }
          ]
        }
      ],

      defaultProps: {
        children: "children",
        label: "label"
      }
    };
  },
  methods: {
    drawPie() {
      const myChart = this.$echarts.init(
        document.getElementById("chartPieBox")
      );
      myChart.setOption({
        title: {
          text: "某站点用户访问来源",
          subtext: "纯属虚构",
          x: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: "left",
          data: ["直接访问", "邮件营销", "联盟广告", "视频广告", "搜索引擎"]
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: "55%",
            center: ["50%", "60%"],
            data: [
              {
                value: 335,
                name: "直接访问"
              },
              {
                value: 310,
                name: "邮件营销"
              },
              {
                value: 234,
                name: "联盟广告"
              },
              {
                value: 135,
                name: "视频广告"
              },
              {
                value: 1548,
                name: "搜索引擎"
              }
            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      });
    }
  },
    mounted() {
    this.drawPie();
  },
}
</script>

<style lang="scss" scoped>
.tree {
  width: 100%;
  height: 100%;
  padding: 20px;
  min-height: calc(100vh - 100px);
  border: 0.5px solid #e2e3e6;
}
  .chartStyle {
    width: 100%;
    height: 100%;
    // border:0.5px solid #e2e3e6;
  }
</style>