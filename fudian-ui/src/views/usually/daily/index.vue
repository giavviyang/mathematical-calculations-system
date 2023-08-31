<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
        <container-top class="mainTop">
          <el-form :inline="true" slot="ipt">
            <el-form-item label="姓名：">
              <el-input v-model="input" placeholder="请输入学生姓名" clearable ref="ipt"/>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="onSubmit">搜索</el-button>
            </el-form-item>
          </el-form>
        </container-top>
        <slot-table class="mainCenter"
                    :pageSize="pageSize"
                    :pageNum="pageNum"
                    ref="slotTable"
                    :total="total"
                    @handleChange="handleChange">
          <el-table
            border
            stripe
            slot="table"
            :data="tableData"
            height="100%">
            <el-table-column
              type="index"
              label="序号">
            </el-table-column>
            <el-table-column
              v-for="item in tableDataColumn"
              :key="item.studentNumber"
              :prop="item.prop"
              :label="item.label">
            </el-table-column>
            <el-table-column align="center" label="操作" min-width="100" max-width="200">
              <template slot-scope="scope">
                <el-button @click="onlinePreview(scope.row)" :refId="scope.row" type="text" size="mini">查看详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </slot-table>
      </div>
    </main-flexible-tree>
    <el-dialog
      :title="dialogTitle"
      class="dialog-style"
      :visible.sync="dialogVisible"
      :close-on-click-modal='false'
      :close-on-press-escape="false"
      width="60%">
      <div class="radarEcharts">
        <div @click="echartsShowHide('')" style="display: flex;justify-content: space-between">
          <span class="radarEchartsTitle">学情分析图：</span>
          <i class="iconfont open" style="font-size: 26px;position: relative;top:2px;color: red;left: -10px">
            {{ tabs === '' ? '&#xe743;' : '&#xe742;' }}
          </i>
        </div>
        <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane :label="item.label" :name="item.activeName" v-for="(item,index) in radarEcharts"
                       :key="item.label">
            <div class="dialogLeft" v-show="activeName===item.activeName">
              <div class="title">{{ item.label }}雷达分析图</div>
              <div :id="item.idName" style="width: 100%;height: 100%;"></div>
            </div>
            <div class="dialogRight" v-show="activeName===item.activeName">
              <el-carousel :autoplay="flag" class="rightRadar">
                <el-carousel-item v-for="(itemList,index) in item.list" :key="index" :label="itemList.label">
                  <div class="title">{{ itemList.label }}雷达分析图</div>
                  <div :id="itemList.algorithmName" style="width: 100%;height: 100%;"></div>
                </el-carousel-item>
              </el-carousel>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="knowledgePointTitle">知识点掌握情况：</div>
      <div class="knowledgePoint">
        <div class="knowledgePointCollapse">
          <div class="failing" @click="failingShowHide('')">
            <div><i class="iconfont">&#xe645;</i><span>未掌握知识点：</span></div>
            <div>
              <i class="iconfont open">{{ failingKnowledge === '' ? '&#xe741;' : '&#xe740;' }}</i>
            </div>
          </div>
          <el-collapse class="failingCollapse">
            <el-collapse-item v-for="(item,index) in failingKnowledgePoint"
                              :key="index" :title="item.classificationMsg" :name="index">
              <div class="collapseLeft">
                <div>总练习题数：<span>{{ item.count }}</span></div>
                <div>正确率：<span>{{ item.correctRate + "%" }}</span></div>
                <div>平均用时：<span>{{ item.averageTime + "秒" }}</span></div>
              </div>
              <el-table
                :data="item.details"
                border>
                <el-table-column
                  prop="detail"
                  label="诊断归因"
                  width="200">
                </el-table-column>
                <el-table-column
                  prop="content"
                  label="错题详情">
                  <template slot-scope="props">
                    {{ props.row.content.replace(/,/g, "&nbsp;&nbsp;  ") }}
                  </template>
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
          <div class="accuracy" @click="accuracyShowHide('')">
            <div><i class="iconfont">&#xe63e;</i><span>已掌握知识点：</span></div>
            <div>
              <i class="iconfont open">{{ accuracyKnowledge === '' ? '&#xe741;' : '&#xe740;' }}</i>
            </div>
          </div>
          <el-collapse class="accuracyCollapse">
            <el-collapse-item v-for="(item,index) in accuracyKnowledgePoint"
                              :key="index" :title="item.classificationMsg" :name="index">
              <div class="collapseLeft">
                <div>总练习题数：<span>{{ item.count }}</span></div>
                <div>正确率：<span>{{ item.correctRate + "%" }}</span></div>
                <div>平均用时：
                  <span>{{ item.averageTime + "秒" }}</span>
                </div>
              </div>
              <el-table
                :data="item.details"
                border>
                <el-table-column
                  prop="detail"
                  label="诊断归因"
                  width="200">
                </el-table-column>
                <el-table-column
                  prop="content"
                  label="错题详情">
                  <template slot-scope="props">
                    {{ props.row.content.replace(/,/g, "&nbsp;&nbsp;  ") }}
                  </template>
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
          <div class="not" @click="notShowHide('')">
            <div><i class="iconfont notColor">&#xe644;</i><span class="notColor">未练习知识点：</span>
            </div>
            <div>
              <i class="iconfont open notColor">{{ notKnowledge === '' ? '&#xe741;' : '&#xe740;' }}</i>
            </div>
          </div>
          <div class="notCollapse">
            <p v-for="(item,index) in notKnowledgePoint" :key="index">{{ item }}</p>
          </div>
          <div class="customs" @click="customsShowHide('')">
            <div><i class="iconfont">&#xe67b;</i><span>{{ customsKnowledgePoint.title }}</span></div>
            <div>
              <i class="iconfont open">{{ customsKnowledge === '' ? '&#xe741;' : '&#xe740;' }}</i>
            </div>
          </div>
          <el-collapse class="customsCollapse">
            <el-collapse-item v-for="(item,index) in customsKnowledgePoint.collapse"
                              :key="index" :title="item.grade+'-第'+item.levelName+'关'" :name='index'>
              <div class="collapseLeft">
                <div>总正确率：
                  <span>{{ item.correctRate }}%</span>
                </div>
                <div>平均用时：
                  <span v-if="item.averageTime != null">{{ item.averageTime }}秒</span>
                  <span v-if="item.averageTime == null">0秒</span>
                </div>
              </div>
              <el-table
                :data="item.details"
                border>
                <el-table-column
                  prop="classificationMsg"
                  label="考察知识点"
                  width="200" :cell-class-name="cellClsNm">
                  <template slot-scope="props">
                    {{ props.row.classificationMsg + '(占比' + props.row.proportion + '%)' }}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="correctRate"
                  width="150"
                  label="正确率">
                  <template slot-scope="props">
                    {{ props.row.correctRate }}%
                  </template>
                </el-table-column>
                <el-table-column
                  prop="wrongQuestion"
                  label="错题">
                  <template slot-scope="scope">
                    <span v-if="scope.row.wrongQuestion"> {{
                        scope.row.wrongQuestion.replace(/,/g, "&nbsp;&nbsp;")
                      }}</span>
                  </template>
                </el-table-column>
              </el-table>
              <p class="relevance">本关关联知识点:</p>
              <div class="relevance" v-for="(item,index) in item.historyDetails"
                   :key="index">
                {{ item.classificationMsg + '' }}以往正确率:
                {{ item.correctRate }}%
                以往错题:
                <span v-show="item.wrongQuestion===''">无</span>
                <span v-show="item.wrongQuestion!==''">{{ item.wrongQuestion.replace(/,/g, "&nbsp;&nbsp;") }}</span>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getDeptTree} from "@/api/system/dept";
import ContainerTop from "@/components/public/MainBody/ContainerTop";
import SlotTable from "@/components/public/table/SlotTable";
import {
  queryStudentInfoByStudent,
  getAlgorithmAmount,
  getKnowledgeAmount,
  getMasterSituation,
  getBreakThroughAnalysis
} from "@/api/usually/daily/analysis"
import {diligenceRanking, progressRanking} from "@/api/usually/transfer";

export default {
  name: "index",
  components: {SlotTable, ContainerTop, MainFlexibleTree},
  created() {
    this.leftDepTree();
    // 全局添加事件监听
    // window.addEventListener('keyup', this.onSubmit)
    // window.addEventListener('keydown', this.handleKeyDown1, true);
    this.keyupSubmit();
  },
  data() {
    return {
      treeData: [],
      tableDataFilter: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      dialogTitle: '',
      tableDataColumn: [
        {label: '姓名', prop: 'studentName'},
        {label: '学生学号', prop: 'studentNumber'},
        {label: '已掌握知识点个数', prop: 'master'},
        {label: '未掌握知识点个数', prop: 'notHave'},
        {label: '未练习知识点个数', prop: 'notPractice'},
      ],
      radarEcharts: [{
        label: '整数',
        activeName: 'first',
        idName: 'integerRadar',
        list: [
          {
            algorithmName: 'integerRadar1',
            label: '加法',
          },
          {
            algorithmName: 'integerRadar2',
            label: '减法',
          },
          {
            algorithmName: 'integerRadar3',
            label: '乘法',
          },
          {
            algorithmName: 'integerRadar4',
            label: '除法',
          },]
      }, {
        label: '小数',
        activeName: 'second',
        idName: 'decimalsRadar',
        list: [
          {
            algorithmName: 'decimalsRadar1',
            label: '加法',
          },
          {
            algorithmName: 'decimalsRadar2',
            label: '减法',
          },
          {
            algorithmName: 'decimalsRadar3',
            label: '乘法',
          },
          {
            algorithmName: 'decimalsRadar4',
            label: '除法',
          },]
      }, {
        label: '分数',
        activeName: 'third',
        idName: 'gradeRadar',
        list: [
          {
            algorithmName: 'gradeRadar1',
            label: '加法',
          },
          {
            algorithmName: 'gradeRadar2',
            label: '减法',
          },
          {
            algorithmName: 'gradeRadar3',
            label: '乘法',
          },
          {
            algorithmName: 'gradeRadar4',
            label: '除法',
          },]
      }],
      algorithm: 1,
      input: '',
      classRoomId: '',
      nodeType: '',
      tableData: [],
      failingKnowledgePoint: [],
      accuracyKnowledgePoint: [],
      notKnowledgePoint: [],
      customsKnowledgePoint: {
        title: '闯关详情分析：',
        collapse: []
      },
      studentName: '',
      studentId: '',
      pageSize: 20,
      pageNum: 1,
      total: 1,
      dialogVisible: false,   //弹框默认隐藏
      flag: false,  //轮播图不自动切换
      tabs: '',
      activeName: 'first',  //tabs切换
      failingKnowledge: '', //未掌握知识点
      accuracyKnowledge: '', //已掌握知识点
      notKnowledge: '', //未练习知识点
      customsKnowledge: '', //关卡知识点
      radarRadius: '',
      radarItemRadius: '',
      params: {
        classRoomId: '',
        pageSize: 20,
        pageNum: 1,
      }
    }
  },
  methods: {
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.onSubmit();
        }
      }
    },
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          this.fore(this.treeData[0])
          this.getTable(this.params);
        }
      })
    },
    handleNodeClick(val, node, component) {
      // console.log(val, node, component)
      this.fore(val)
      this.params.pageSize = 20;
      this.params.pageNum = 1;
      this.getTable(this.params);

    },
    /*循环查找tree树中的班级节点*/
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.classRoomId = obj.id
        this.params.classRoomId = obj.id
      }
      return;
    },
    getTable(params) {
      this.tableData = [];
      queryStudentInfoByStudent(params).then(response => {
        if (response.code === 200) {
          this.tableData = response.rows;
          console.log('111', this.$refs.slotTable)
          // console.log(response)
          this.total = response.total;
          this.pageSize = response.pageSize;
          this.pageNum = response.pageNum;
        }
      })
    },
    onSubmit() {
      let params = JSON.parse(JSON.stringify(this.params));
      params.studentName = this.input;
      queryStudentInfoByStudent(params).then(response => {
        if (response.code === 200) {
          this.tableData = response.rows
          this.pageNum = response.pageNum
          this.pageSize = response.pageSize
          this.total = response.total
        }
      })
    },
    // clear() {
    //   if (this.$refs.ipt.clear) {
    //     this.getTable(this.params)
    //   }
    // },
    onlinePreview(row) {
      this.studentName = row.studentName;
      this.studentId = row.studentId;
      this.dialogVisible = true;
      this.dialogTitle = this.studentName + "学情分析";
      this.activeName = 'first';
      this.accuracyKnowledge = '';
      this.failingKnowledge = '';
      this.notKnowledge = '';
      this.customsKnowledge = '';
      this.accuracyKnowledgePoint = [];
      this.failingKnowledgePoint = [];
      this.notKnowledgePoint = []
      this.customsKnowledgePoint = {
        title: '闯关详情分析：',
        collapse: []
      };
      if(document.querySelector('.accuracyCollapse')){
        document.querySelector('.accuracyCollapse').style.display = 'none'
      }
      if(document.querySelector('.failingCollapse')){
        document.querySelector('.failingCollapse').style.display = 'none'
      }
      if(document.querySelector('.notCollapse')){
        document.querySelector('.notCollapse').style.display = 'none'
      }
      if(document.querySelector('.customsCollapse')){
        document.querySelector('.customsCollapse').style.display = 'none'
      }
      this.$nextTick(() => {
        if (document.body.clientWidth >= 1800) {
          this.radarRadius = 100
          this.radarItemRadius = 100
        } else if (document.body.clientWidth >= 1400) {
          this.radarRadius = 95
          this.radarItemRadius = 85
        } else {
          this.radarRadius = 85
          this.radarItemRadius = 60
        }
        getKnowledgeAmount({studentId: row.studentId, numberType: this.activeName}).then(response => {
          if (response.code === 200) {
            // console.log('response2222', response)
            this.knowledgeRadar(response.data.algorithm1, 'integerRadar1', this.radarItemRadius, '加法', '#FAC988');
            this.knowledgeRadar(response.data.algorithm2, 'integerRadar2', this.radarItemRadius, '减法', '#FC9D9A');
            this.knowledgeRadar(response.data.algorithm3, 'integerRadar3', this.radarItemRadius, '乘法', '#C8C8A9');
            this.knowledgeRadar(response.data.algorithm4, 'integerRadar4', this.radarItemRadius, '除法', '#83AF9B');
            var texts = [];
            var kValue = [];
            texts[0] = response.data.algorithm1.length;
            texts[1] = response.data.algorithm2.length;
            texts[2] = response.data.algorithm3.length;
            texts[3] = response.data.algorithm4.length;
            getAlgorithmAmount({studentId: row.studentId, numberType: this.activeName}).then(response => {
              // console.log('1111',response)
              if (response.code === 200) {
                kValue[0] = ((response.data.algorithm1) / texts[0] * 100).toFixed(2);
                kValue[1] = ((response.data.algorithm2) / texts[1] * 100).toFixed(2);
                kValue[2] = ((response.data.algorithm3) / texts[2] * 100).toFixed(2);
                kValue[3] = ((response.data.algorithm4) / texts[3] * 100).toFixed(2);
                this.numberFieldRadar('integerRadar', this.radarRadius, kValue, '整数', "#63B2ED");
              }
            })
          }
        });
      })

      // getMasterSituation({studentId: row.studentId}).then(response => {
      //   if (response.code === 200) {
      //     /*未掌握知识点*/
      //     this.failingKnowledgePoint = response.data.state0;
      //     /*掌握知识点*/
      //     this.accuracyKnowledgePoint = response.data.state1;
      //     /*未练习知识点*/
      //     this.notKnowledgePoint = response.data.state2;
      //   }
      // });

      // getBreakThroughAnalysis({studentId: row.studentId}).then(response => {
      //   if (response.code === 200) {
      //     // console.log(response)
      //     this.customsKnowledgePoint.collapse = response.data;
      //   }
      // });
    },
    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '错题') {
        return 'errorList'
      }
    },

    handleChange(pageSize, pageNum) {
      // console.log(pageSize, pageNum)
      this.params.pageSize = pageSize;
      this.params.pageNum = pageNum;
      this.getTable(this.params);
    },

    /**
     * tabs切换
     * @param tab
     * @param event
     */
    handleClick(tab, event) {
      this.activeName = tab.name;
      if (this.activeName === 'first') {
        if (document.body.clientWidth >= 1800) {
          this.radarRadius = 100
          this.radarItemRadius = 100
        } else if (document.body.clientWidth >= 1400) {
          this.radarRadius = 95
          this.radarItemRadius = 85
        } else {
          this.radarRadius = 85
          this.radarItemRadius = 60
        }
        getKnowledgeAmount({
          studentId: this.studentId,
          numberType: this.activeName,
          algorithm: this.algorithm
        }).then(response => {
          if (response.code === 200) {
            console.log('F response1111', response)
            this.knowledgeRadar(response.data.algorithm1, 'integerRadar1', this.radarItemRadius, '加法', '#FAC988');
            this.knowledgeRadar(response.data.algorithm2, 'integerRadar2', this.radarItemRadius, '减法', '#FC9D9A');
            this.knowledgeRadar(response.data.algorithm3, 'integerRadar3', this.radarItemRadius, '乘法', '#C8C8A9');
            this.knowledgeRadar(response.data.algorithm4, 'integerRadar4', this.radarItemRadius, '除法', '#83AF9B');
            var texts = [];
            var kValue = [];
            texts[0] = response.data.algorithm1.length;
            texts[1] = response.data.algorithm2.length;
            texts[2] = response.data.algorithm3.length;
            texts[3] = response.data.algorithm4.length;
            getAlgorithmAmount({studentId: this.studentId, numberType: this.activeName}).then(response => {
              if (response.code === 200) {
                console.log('F response2222', response)
                kValue[0] = ((response.data.algorithm1) / texts[0] * 100).toFixed(2);
                kValue[1] = ((response.data.algorithm2) / texts[1] * 100).toFixed(2);
                kValue[2] = ((response.data.algorithm3) / texts[2] * 100).toFixed(2);
                kValue[3] = ((response.data.algorithm4) / texts[3] * 100).toFixed(2);
                this.numberFieldRadar('integerRadar', this.radarRadius, kValue, '整数', "#63B2ED");
              }
            })
          }
        });
      } else if (this.activeName === 'second') {
        this.$nextTick(() => {
          if (document.body.clientWidth >= 1800) {
            this.radarRadius = 100
            this.radarItemRadius = 100
          } else if (document.body.clientWidth >= 1400) {
            this.radarRadius = 95
            this.radarItemRadius = 85
          } else {
            this.radarRadius = 85
            this.radarItemRadius = 60
          }
          getKnowledgeAmount({
            studentId: this.studentId,
            numberType: this.activeName,
            algorithm: this.algorithm
          }).then(response => {
            if (response.code === 200) {
              console.log('S response1111', response)
              this.knowledgeRadar(response.data.algorithm1, 'decimalsRadar1', this.radarItemRadius, '加法', '#B0D7F8');
              this.knowledgeRadar(response.data.algorithm2, 'decimalsRadar2', this.radarItemRadius, '减法', '#B6BB5E');
              this.knowledgeRadar(response.data.algorithm3, 'decimalsRadar3', this.radarItemRadius, '乘法', '#C9B3E1');
              this.knowledgeRadar(response.data.algorithm4, 'decimalsRadar4', this.radarItemRadius, '除法', '#F4E5BA');
              var texts = [];
              var kValue = [];
              texts[0] = response.data.algorithm1.length;
              texts[1] = response.data.algorithm2.length;
              texts[2] = response.data.algorithm3.length;
              texts[3] = response.data.algorithm4.length;
              getAlgorithmAmount({studentId: this.studentId, numberType: this.activeName}).then(response => {
                if (response.code === 200) {
                  console.log('S response2222', response)
                  kValue[0] = ((response.data.algorithm1) / texts[0] * 100).toFixed(2);
                  kValue[1] = ((response.data.algorithm2) / texts[1] * 100).toFixed(2);
                  kValue[2] = ((response.data.algorithm3) / texts[2] * 100).toFixed(2);
                  kValue[3] = ((response.data.algorithm4) / texts[3] * 100).toFixed(2);
                  // console.log(texts, kValue)
                  this.numberFieldRadar('decimalsRadar', this.radarRadius, kValue, '小数', "#9CB7BE");
                }
              })
            }
          });
        })
      } else if (this.activeName === 'third') {
        this.$nextTick(() => {
          if (document.body.clientWidth >= 1800) {
            this.radarRadius = 100
            this.radarItemRadius = 100
          } else if (document.body.clientWidth >= 1400) {
            this.radarRadius = 95
            this.radarItemRadius = 85
          } else {
            this.radarRadius = 85
            this.radarItemRadius = 60
          }
          getKnowledgeAmount({studentId: this.studentId, numberType: this.activeName}).then(response => {
            if (response.code === 200) {
              console.log('T response1111', response)
              this.knowledgeRadar(response.data.algorithm1, 'gradeRadar1', this.radarItemRadius, '加法', '#206CC0');
              this.knowledgeRadar(response.data.algorithm2, 'gradeRadar2', this.radarItemRadius, '减法', '#DD578C');
              this.knowledgeRadar(response.data.algorithm3, 'gradeRadar3', this.radarItemRadius, '乘法', '#EAA055');
              this.knowledgeRadar(response.data.algorithm4, 'gradeRadar4', this.radarItemRadius, '除法', '#8D94C2');
              var texts = [];
              var kValue = [];
              texts[0] = response.data.algorithm1.length;
              texts[1] = response.data.algorithm2.length;
              texts[2] = response.data.algorithm3.length;
              texts[3] = response.data.algorithm4.length;
              getAlgorithmAmount({studentId: this.studentId, numberType: this.activeName}).then(response => {
                if (response.code === 200) {
                  console.log('T response2222', response)
                  kValue[0] = ((response.data.algorithm1) / texts[0] * 100).toFixed(2);
                  kValue[1] = ((response.data.algorithm2) / texts[1] * 100).toFixed(2);
                  kValue[2] = ((response.data.algorithm3) / texts[2] * 100).toFixed(2);
                  kValue[3] = ((response.data.algorithm4) / texts[3] * 100).toFixed(2);
                  this.numberFieldRadar('gradeRadar', this.radarRadius, kValue, '分数', "#99D8DD");
                }
              })
            }
          });
        })
      }
    },
    /**
     * 展开知识点
     */
    echartsShowHide(string) {
      let tabs = document.getElementsByClassName('el-tabs')[0];
      let knowledgePoint = document.querySelector('.knowledgePoint');
      // console.log(knowledgePoint.style)
      if (this.tabs === string) {
        this.tabs = 'tabs';
        tabs.style.display = 'none';
        knowledgePoint.style.maxHeight = 'calc(80vh)';
      } else {
        this.tabs = '';
        tabs.style.display = 'block';
        knowledgePoint.style.maxHeight = 'calc(25vh)';
      }
    },
    failingShowHide(string) {
      let failingCollapse = document.getElementsByClassName('failingCollapse')[0];
      if (this.failingKnowledge === string) {
        this.failingKnowledge = 'failingKnowledgePoint';
        failingCollapse.style.display = 'block';

        getMasterSituation({studentId: this.studentId}).then(response => {
          if (response.code === 200) {
            /*未掌握知识点*/
            this.failingKnowledgePoint = response.data.state0;
            /*掌握知识点*/
            // this.accuracyKnowledgePoint = response.data.state1;
            /*未练习知识点*/
            // this.notKnowledgePoint = response.data.state2;
          }
        });
      } else {
        this.failingKnowledge = '';
        failingCollapse.style.display = 'none';
      }
    },
    accuracyShowHide(string) {
      let accuracyCollapse = document.getElementsByClassName('accuracyCollapse')[0];
      if (this.accuracyKnowledge === string) {
        this.accuracyKnowledge = 'accuracyKnowledge';
        accuracyCollapse.style.display = 'block';
        getMasterSituation({studentId: this.studentId}).then(response => {
          if (response.code === 200) {
            /*未掌握知识点*/
            // this.failingKnowledgePoint = response.data.state0;
            /*掌握知识点*/
            this.accuracyKnowledgePoint = response.data.state1;
            /*未练习知识点*/
            // this.notKnowledgePoint = response.data.state2;
          }
        });
      } else {
        this.accuracyKnowledge = '';
        accuracyCollapse.style.display = 'none';
      }
    },
    notShowHide(string) {
      // console.log('111', this.notKnowledge)
      let notCollapse = document.getElementsByClassName('notCollapse')[0];
      if (this.notKnowledge === string) {
        this.notKnowledge = 'notKnowledge';
        notCollapse.style.display = 'block';
        getMasterSituation({studentId: this.studentId}).then(response => {
          if (response.code === 200) {
            /*未掌握知识点*/
            // this.failingKnowledgePoint = response.data.state0;
            /*掌握知识点*/
            // this.accuracyKnowledgePoint = response.data.state1;
            /*未练习知识点*/
            this.notKnowledgePoint = response.data.state2;
          }
        });
      } else {
        this.notKnowledge = '';
        notCollapse.style.display = 'none';
      }
    },
    customsShowHide(string) {
      let customsCollapse = document.getElementsByClassName('customsCollapse')[0];
      if (this.customsKnowledge === string) {
        this.customsKnowledge = 'customsKnowledge';
        customsCollapse.style.display = 'block';
        getBreakThroughAnalysis({studentId: this.studentId}).then(response => {
          if (response.code === 200) {
            // console.log(response)
            this.customsKnowledgePoint.collapse = response.data;
          }
        });
      } else {
        this.customsKnowledge = '';
        customsCollapse.style.display = 'none';
      }
    },
    /**
     * 雷达图
     */
    //数域雷达图
    numberFieldRadar(radarId, radarRadius, data, numField, color) {
      // 基于准备好的dom，初始化echarts实例
      radarId = this.$echarts.init(document.getElementById(radarId));
      // 绘制图表
      radarId.setOption({
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          left: 'center',
        },
        radar: [
          {
            indicator: [
              {text: '加法', max: 100},
              {text: '减法', max: 100},
              {text: '乘法', max: 100},
              {text: '除法', max: 100},
            ],
            // indicator:texts,
            nameGap: 5, // 图中工艺等字距离图的距离
            // radius:65,
            radius: radarRadius,
            center: ['50%', '50%'],
          },
        ],
        series: [
          {
            type: 'radar',
            symbolSize: 0, //拐点大小为0
            tooltip: {
              trigger: 'item'
            },
            areaStyle: {},
            data: [
              {
                value: data,
                name: numField + '掌握知识点占比',
                itemStyle: {
                  color: color // 图表中各个图区域的颜色
                }
              },
            ]
          },
        ]
      });
    },
    //知识点雷达图
    knowledgeRadar(params, radarId, radarItemRadius, name, color) {
      var texts = [];
      var kValue = [];
      if (params.length > 0) {
        for (var ii = 0; ii < params.length; ii++) {
          texts.push({text: params[ii].text, min: 0});
          kValue.push(params[ii].max);
        }
      } else {
        return false
      }
      // console.log(texts,kValue)
      // 基于准备好的dom，初始化echarts实例
      radarId = this.$echarts.init(document.getElementById(radarId));
      // 绘制图表
      radarId.setOption({
        tooltip: {  //雷达图Tooltip
          trigger: 'axis',
          position: [50, 0],
          padding: [0, 10],
          extraCssText: 'white-space: normal; word-break: break-all;'
        },
        legend: { //雷达图 Legend
          left: 'center',
        },
        radar: [  //雷达图 radar
          {
            name: {

              formatter: function (value) {
                let list = value.split("");
                let result = "";
                for (let i = 1; i <= list.length; i++) {
                  if (!(i % 12) && list[i] != undefined) {
                    result += list[i - 1] + '\n';
                  } else {
                    result += list[i - 1];
                  }
                }
                return result
              }
            },
            indicator: texts,
            nameGap: 20, // 图中工艺等字距离图的距离
            radius: radarItemRadius,
            center: ['50%', '50%'],
            align: 'left'
          },
        ],
        series: [
          {
            type: 'radar',
            symbolSize: 0, //拐点大小为0
            tooltip: {
              trigger: 'item'
            },
            areaStyle: {},
            data: [
              {
                value: kValue,//[60, 73, 85, 90, 80, 70, 65],
                name: name + '知识点最高分',
                itemStyle: {
                  color: color // 图表中各个图区域的颜色

                },
              },
            ]
          },
        ]
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.mainTop {
  height: 67px;
}

.mainCenter {
  height: calc(100% - 67px);

  /deep/ .table {
    height: calc(100% - 40px);
  }
}

.radarEcharts {
  width: 100%;

  .radarEchartsTitle {
    font-size: 16px;
    letter-spacing: 1px;
  }

  /deep/ .el-tabs__content {
    padding: 5px 0;
    height: calc(50vh);

    .el-tab-pane {
      display: flex;
      height: calc(100% - 10px);

      .title {
        font-size: 14px;
        text-align: left;
        margin-left: 10px;
      }

      .dialogLeft {
        width: 40%;
      }

      .dialogRight {
        width: 60%;

        .rightRadar {
          height: 100%;

          .el-carousel__container {
            height: 100%;

          }

          .el-carousel__item {
            display: flex;
            flex-direction: column;
            align-items: self-start;

          }

          .el-carousel__indicators {
            width: 100%;
            display: inline-flex;
            justify-content: space-around;

            li {
              padding: 0;

              .el-carousel__button {
                width: 80px;
                height: 30px;
                border-radius: 20px;
                background: #46A6FF;
                color: #fff;
              }
            }

          }

        }

      }
    }
  }

}

.knowledgePointTitle {
  font-size: 16px;
  letter-spacing: 1px;
  margin-top: 5px;
}

.notColor {
  color: #aaa;
}

.knowledgePoint {
  width: 100%;
  //max-height: 400px;
  max-height: calc(25vh);
  overflow: auto;

  .knowledgePointCollapse {
    .failing, .accuracy, .not, .customs {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 10px;
      font-size: 14px;
      font-weight: 600;
      letter-spacing: 1px;
      color: #00afff;
      cursor: pointer;

      span {
        position: relative;
        top: -2px;
        left: 5px;
      }

      .iconfont {
        font-size: 20px;
      }

      .open {
        font-size: 28px;
      }
    }

    .failingCollapse, .accuracyCollapse, .notCollapse, .customsCollapse {
      display: none;
    }

    /deep/ .el-collapse {
      border-top: none;


      .el-collapse-item__header {
        height: 38px;
        line-height: 38px;
        padding: 0 10px;
        color: red;
      }

      .el-collapse-item__wrap {
        padding: 0 20px;

        .el-collapse-item__content {
          padding-bottom: 5px;

          .collapseLeft {
            display: flex;
            justify-content: space-around;
            align-items: center;
            margin: 5px 0;

            div {
              width: 25%;

              span {
                color: #00afff;
                font-weight: 600;
              }
            }
          }

          .el-table {
            .cell {
              word-break: break-word;
            }

            .el-table__header-wrapper {
              .cell {
                text-align: center;
                color: red;
              }
            }

            .el-table__body-wrapper {
              td {
                .cell {
                  padding-left: 10px;
                  word-break: break-word;
                  color: red;
                  text-align: left;
                }
              }

              td:first-child {
                .cell {
                  color: #00afff;

                }
              }

            }
          }

          .relevance {
            color: red;
            font-weight: 500;
            line-height: 38px;
            padding-left: 10px;
            word-break: break-word;
          }
        }

      }
    }

    /deep/ .notCollapse {
      p {
        margin-left: 20px;
        color: #aaa;

      }
    }

    /deep/ .customsCollapse {
      .el-table {
        .el-table__body-wrapper {
          td:nth-child(2) {
            .cell {
              text-align: center;
            }
          }

          .errorList {
            .cell {
              padding-left: 10px;
              word-break: break-word;
              color: red;
              text-align: left;
              white-space: pre-line;
            }
          }
        }
      }
    }
  }
}
</style>
