<template>
  <div class="dashboard-editor-container" >
    <panel-group @handleSetLineChartData="handleSetLineChartData" />
    <el-row :gutter="40">
      <el-col :xs="24" :sm="24" :lg="8" style="width: 50%">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8" style="width: 50%">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="40">
      <el-col :xs="{span: 24}"
      :sm="{span: 24}"
      :md="{span: 24}"
      :lg="{span: 12}"
      :xl="{span: 12}" style="width: 50%">
        <transaction-table />
      </el-col>
      <el-col
      :xs="{span: 24}"
      :sm="{span: 12}"
      :md="{span: 12}"
      :lg="{span: 6}"
      :xl="{span: 6}" style="width: 50%">
        <moban />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import PieChart from './components/PieChart'
import BarChart from './components/BarChart'
import TransactionTable from './components/TransactionTable'
import moban from './components/moban'
import BoxCard from './components/BoxCard'

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup,
    PieChart,
    BarChart,
    TransactionTable,
    moban,
    BoxCard
  },
  data() {
    return {
        container: {height: ''},
    }
  },
  methods: {
      getHeight() {
        let containerH = window.innerHeight-84;
        this.container.height = containerH + 'px';  //获取浏览器高度减去顶部导航栏
      },
      // 首页 累计 访问事件
      handleSetLineChartData(type) {
        console.log('脑壳疼')
      }
  },
  created() {
    window.addEventListener('resize', this.getHeight);
    this.getHeight()
    this.handleSetLineChartData()
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 20px;
  width: 100%;
  // background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 10px 10px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
