<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" :defaultExpand="defaultExpand"
                        @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
        <container-top class="mainTop">
          <el-form :inline="true" slot="btnDrop">
            <el-form-item>
              <el-button size="mini" type="primary" @click="addKnowledgeTable">新增</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="updateKnowledgeTable">编辑</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="delKnowledgeTable">删除</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="orderKnowledgemaintain">排序</el-button>
            </el-form-item>
          </el-form>
        </container-top>
        <slot-table class="mainCenter"
                    :pageSize="pageSize"
                    :pageNum="pageNum"
                    :total="total"
                    @handleChange="handleChange">
          <el-table
            border
            stripe
            slot="table"
            :data="tableData"
            height="100%"
            @selection-change="handleSelectionChange"
            row-key="classificationId"
            :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
            :cell-class-name="addClass"
            lazy
            :load="load"
            ref="tableSelect">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column
              width="70"
              label="序号" prop="parentIndex" type="">
              <template slot-scope="scope">
                <span v-if="scope.row.isIndex">{{ scope.row.parentIndex }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="classificationMsg"
              label="分类名称" :min-width="getMaxLength">
              <template slot-scope="scope">
                <span>{{ scope.row.classificationMsg }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="classificationNickname"
              label="分类别名"
              width="150">
            </el-table-column>
            <el-table-column
              prop="example"
              label="例题"
              width="180">
              <template slot-scope="scope">
                <p v-for="(item,index) in scope.row.popupList" :key="index"
                   style="display: flex;flex-direction: column">
                  <span>{{ item.formulaOne }}</span>
                  <span>{{ item.formulaTwo }}</span>
                </p>
              </template>
            </el-table-column>
            <el-table-column
              prop="classificationDatarange"
              label="数据范围"
              width="80">
            </el-table-column>
            <el-table-column
              prop="classificationTime"
              label="限制用时"
              width="80">
            </el-table-column>
            <el-table-column
              prop="diagnosisMsg"
              label="诊断"
              min-width="180">
            </el-table-column>
            <el-table-column
              prop="questionsNumber"
              label="出题数"
              width="80">
            </el-table-column>
            <el-table-column
              prop="content"
              label="与整数运算的对应"
              v-if="flag" width="250">
              <template slot-scope="scope">
                <p v-for="(item,index) in scope.row.popupList" :key="index"
                   style="display: flex;flex-direction: column">
                  <span>{{ item.content }}</span>
                </p>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="100">
              <template slot-scope="scope">
                <el-button @click="handleClickBtn(scope.row)" type="text" size="small">设置规则</el-button>
              </template>
            </el-table-column>
          </el-table>
        </slot-table>
      </div>
    </main-flexible-tree>
    <!--表格新增弹框(除小数)-->
    <el-dialog :visible.sync="addTableDialog"
               width="30%"
               class=" dialog-style dialog-basic addTableDialog"
               title="新增知识点">
      <dialog-add-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="知识点名称：" prop="classificationMsg">
            <el-input placeholder="请输入知识点名称" v-model="knowledge.classificationMsg"></el-input>
          </el-form-item>
          <el-form-item label="知识点别名：" prop="classificationMsg">
            <el-input placeholder="请输入知识点别名" v-model="knowledge.classificationNickName"></el-input>
          </el-form-item>
          <el-form-item label="出题数：" prop="questionsNumber">
            <el-input v-model.number="knowledge.questionsNumber"></el-input>
          </el-form-item>
          <el-form-item label="限制用时：" prop="classificationTime">
            <el-input v-model.number="knowledge.classificationTime"></el-input>
          </el-form-item>
          <el-form-item label="数据范围：" prop="classificationDatarange">
            <el-input v-model.number="knowledge.classificationDatarange"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge.popupList[0]" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="例题：" prop="formulaOne">
            <el-input v-model="knowledge.popupList[0].formulaOne"></el-input>
          </el-form-item>
          <el-form-item label="例题：" prop="formulaTwo">
            <el-input v-model="knowledge.popupList[0].formulaTwo"></el-input>
          </el-form-item>
          <el-form-item label="诊断：" prop="classificationMsg">
            <el-input type="textarea" v-model="knowledge.diagnosisMsg"></el-input>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
         <el-button size="mini" @click="closeTableRule('knowledgeValidateForm')">取 消</el-button>
        <el-button size="mini" type="primary" @click="addTableNode()">确 定</el-button>
      </span>
    </el-dialog>
    <!--表格新增弹框(小数)-->
    <el-dialog :visible.sync="addTableDialogXiao"
               width="60%"
               class=" dialog-style dialog-basic addTableDialogXiao"
               title="新增知识点">
      <dialog-add-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="知识点名称：" prop="classificationMsg">
            <el-input placeholder="请输入知识点名称" v-model="knowledge.classificationMsg"></el-input>
          </el-form-item>
          <el-form-item label="知识点别名：" prop="classificationMsg">
            <el-input placeholder="请输入知识点别名" v-model="knowledge.classificationNickName"></el-input>
          </el-form-item>
          <el-form-item label="出题数：" prop="questionsNumber">
            <el-input v-model.number="knowledge.questionsNumber"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="限制用时：" prop="classificationTime">
            <el-input v-model.number="knowledge.classificationTime"></el-input>
          </el-form-item>
          <el-form-item label="数据范围：" prop="classificationDatarange">
            <el-input v-model.number="knowledge.classificationDatarange"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge.popupList[0]" class="popupList" ref="knowledgeValidateForm"
                 v-for="(popup,index) in knowledge.popupList" :key="index">
          <el-form-item label="与整数运算的对应：" >
            <el-input type="textarea" placeholder="请输入与整数运算的对应" v-model="popup.content"></el-input>
          </el-form-item>
          <p class="liti">
            <el-form-item label="整数例题：">
              <el-input v-model="popup.formulaOne"></el-input>
            </el-form-item>
            <el-form-item label="小数例题：">
              <el-input v-model="popup.formulaTwo"></el-input>
            </el-form-item>
          </p>
          <el-form-item class="popupsBtn">
            <el-button @click="addRegulationName(knowledge.popupList,popup)">新增
            </el-button>
            <el-button :style="index===0?{display:'none'}:''"
                       @click.prevent="removeRegulation(knowledge.popupList,popup,index)">移除
            </el-button>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules"
                 style="justify-content: flex-start">
          <el-form-item label="诊断：" prop="classificationMsg">
            <el-input type="textarea" v-model="knowledge.diagnosisMsg"></el-input>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
<!--        <el-button size="mini" @click="addTableDialogXiao=false;knowledge=''">取 消</el-button>-->
        <el-button size="mini" @click="closeTableRule('knowledgeValidateForm')">取 消</el-button>
        <el-button size="mini" type="primary" @click="addTableNode()">确 定</el-button>
      </span>
    </el-dialog>
    <!--表格编辑弹框(除小数）-->
    <el-dialog :visible.sync="updateTableDialog"
               width="30%"
               class=" dialog-style dialog-basic addTableDialog"
               title="编辑知识点">
      <dialog-add-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="知识点名称：" prop="classificationMsg">
            <el-input placeholder="请输入知识点名称" v-model="knowledge.classificationMsg"></el-input>
          </el-form-item>
          <el-form-item label="知识点别名：" prop="classificationMsg">
            <el-input placeholder="请输入知识点别名" v-model="knowledge.classificationNickname"></el-input>
          </el-form-item>
          <el-form-item label="出题数：" prop="questionsNumber">
            <el-input v-model.number="knowledge.questionsNumber"></el-input>
          </el-form-item>
          <el-form-item label="限制用时：" prop="classificationTime">
            <el-input v-model.number="knowledge.classificationTime"></el-input>
          </el-form-item>
          <el-form-item label="数据范围：" prop="classificationDatarange">
            <el-input v-model.number="knowledge.classificationDatarange"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="例题：" prop="formulaOne">
            <el-input v-model="knowledge.popupList[0].formulaOne"></el-input>
          </el-form-item>
          <el-form-item label="例题：" prop="formulaTwo">
            <el-input v-model="knowledge.popupList[0].formulaTwo"></el-input>
          </el-form-item>
          <el-form-item label="诊断：" prop="classificationMsg">
            <el-input type="textarea" v-model="knowledge.diagnosisMsg"></el-input>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="closeTableRule('knowledgeValidateForm')">取 消</el-button>
        <el-button size="mini" type="primary" @click="updateTableNode()">确 定</el-button>
      </span>
    </el-dialog>
    <!--表格编辑弹框(小数）-->
    <el-dialog :visible.sync="updateTableDialogXiao"
               width="60%"
               class=" dialog-style dialog-basic addTableDialogXiao"
               title="编辑知识点">
      <dialog-add-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="知识点名称：" prop="classificationMsg">
            <el-input placeholder="请输入知识点名称" v-model="knowledge.classificationMsg"></el-input>
          </el-form-item>
          <el-form-item label="知识点别名：" prop="classificationMsg">
            <el-input placeholder="请输入知识点别名" v-model="knowledge.classificationNickname"></el-input>
          </el-form-item>
          <el-form-item label="出题数：" prop="questionsNumber">
            <el-input v-model.number="knowledge.questionsNumber"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules">
          <el-form-item label="限制用时：" prop="classificationTime">
            <el-input v-model.number="knowledge.classificationTime"></el-input>
          </el-form-item>
          <el-form-item label="数据范围：" prop="classificationDatarange">
            <el-input v-model.number="knowledge.classificationDatarange"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge.popupList[0]" class="popupList"
                 v-for="(popup,index) in knowledge.popupList" :key="index">
          <el-form-item label="与整数运算的对应：" class="popupsItem">
            <el-input type="textarea" placeholder="请输入与整数运算的对应" v-model="popup.content"></el-input>
          </el-form-item>
          <p class="liti">
            <el-form-item label="整数例题：">
              <el-input v-model="popup.formulaOne"></el-input>
            </el-form-item>
            <el-form-item label="小数例题：">
              <el-input v-model="popup.formulaTwo"></el-input>
            </el-form-item>
          </p>
          <el-form-item class="popupsBtn">
            <el-button @click="addRegulationName(knowledge.popupList,popup)">新增
            </el-button>
            <el-button :style="index===0?{display:'none'}:''"
                       @click.prevent="removeRegulation(knowledge.popupList,popup,index)">移除
            </el-button>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="knowledge" ref="knowledgeValidateForm" :rules="formRules"
                 style="justify-content: flex-start">
          <el-form-item label="诊断：" prop="classificationMsg">
            <el-input type="textarea" v-model="knowledge.diagnosisMsg"></el-input>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="closeTableRule('knowledgeValidateForm')">取 消</el-button>
        <el-button size="mini" type="primary" @click="updateTableNode()">确 定</el-button>
      </span>
    </el-dialog>
    <!--设置规则弹框(除分数）-->
    <el-dialog :visible.sync="tableRulesDialog"
               width="90%"
               class=" dialog-style dialog-basic tableRulesDialog"
               title="设置规则">
      <dialog-add-form>
        <el-form slot="form" :model="tableRulesForm">
          <el-form-item label="数域：">
            <el-input v-model="tableRulesForm.numberField" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="运算法则：">
            <el-input v-model="tableRulesForm.algorithm" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="类型：">
            <el-input v-model="tableRulesForm.numType" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm">
          <el-form-item label="分类名称：" class="systematicName">
            <el-input v-model="tableRulesForm.classificationMsg" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="元素个数：">
            <el-input v-model="tableRulesForm.excerciseElementNum"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules">
          <div class="title">{{ tableRulesForm.byRegulationName }}：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.numFirst"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.firstMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.numFirstLen"></el-input>
          </el-form-item>
          <el-form-item label="倍率" v-if="flag">
            <el-input v-model="tableRulesForm.firstMagnify"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules">
          <div class="title">{{tableRulesForm.byRegulationName.slice(1, 3)}}：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.numSecond"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.secondMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.numSecondLen"></el-input>
          </el-form-item>
          <el-form-item label="倍率" v-if="flag">
            <el-input v-model="tableRulesForm.secondMagnify"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules">
          <div class="title">{{tableRulesForm.byRegulationName.slice(1, 3)}}：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.numThree"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.threeMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model.number="tableRulesForm.numThreeLen"></el-input>
          </el-form-item>
          <el-form-item label="倍率" v-if="flag">
            <el-input v-model="tableRulesForm.threeMagnify"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" style="justify-content: flex-start;">
          <el-form-item label="题目数位要求：" :style="this.jinOrTui?'':{width:'150%'}">
            <el-input v-model="tableRulesForm.elementRulers"></el-input>
          </el-form-item>
          <el-form-item :label="tableRulesForm.jinOrTuiName" v-if="jinOrTui">
            <el-radio-group v-model="tableRulesForm.digitPostion" v-show="tableRulesForm.jinOrTuiName.includes('进位')"
                            style="display: flex;justify-content: space-between;">
              <el-radio label="1">个位进位</el-radio>
              <el-radio label="2">十分进位</el-radio>
              <el-radio label="3">连续进位</el-radio>
            </el-radio-group>
            <el-radio-group v-model="tableRulesForm.digitPostion" v-show="tableRulesForm.jinOrTuiName.includes('退位')"
                            v-if="!flag" style="display: flex;justify-content: space-between;">
              <el-radio label="1">个位退位</el-radio>
              <el-radio label="2">十分退位</el-radio>
              <el-radio label="3">连续退位</el-radio>
            </el-radio-group>
            <el-radio-group v-model="tableRulesForm.digitPostion" v-show="tableRulesForm.jinOrTuiName.includes('退位')"
                            v-if="flag" style="display: flex;justify-content: space-between;">
              <el-radio label="1">百分退位</el-radio>
              <el-radio label="2">十分退位</el-radio>
              <el-radio label="3">连续退位</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="closeTableRule()">取 消</el-button>
            <el-button size="mini" type="primary" @click="saveTableRule()">确 定</el-button>
          </span>
    </el-dialog>
    <!--设置规则弹框(分数）-->
    <el-dialog :visible.sync="tableRulesDialogFen"
               width="60%"
               class=" dialog-style dialog-basic tableRulesDialog"
               title="设置规则">
      <dialog-add-form>
        <el-form slot="form" :model="tableRulesForm">
          <el-form-item label="数域：">
            <el-input v-model="tableRulesForm.numberField" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="运算法则：">
            <el-input v-model="tableRulesForm.algorithm" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="类型：">
            <el-input v-model="tableRulesForm.numType" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm">
          <el-form-item label="分类名称：" class="systematicName">
            <el-input v-model="tableRulesForm.classificationMsg" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="元素个数：">
            <el-input v-model="tableRulesForm.excerciseElementNum"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="this.algorithm!=='除法'">
          <el-form-item label="分母是否相同" prop="type" v-show="this.algorithm==='加法'||this.algorithm==='减法'">
            <el-radio-group v-model="tableRulesForm.carryRulers" disabled>
              <el-radio label="1">同分母</el-radio>
              <el-radio label="2">异分母</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="异分母间关系" prop="type" v-show="this.numType === '异分母'">
            <el-radio-group v-model="tableRulesForm.digitPostion">
              <el-radio label="1">分母互质</el-radio>
              <el-radio label="2">分母倍数关系</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="是否可约分" prop="type" v-show="this.algorithm==='乘法'">
            <el-radio-group v-model="tableRulesForm.carryRulers" disabled>
              <el-radio label="1">不可约分</el-radio>
              <el-radio label="2">可约分</el-radio>
            </el-radio-group>
          </el-form-item>

        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-show="this.algorithm!=='乘法'&&this.algorithm!=='除法'">
          <div class="title" style="width: 25.5%">分子：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.molecule"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.moleculeMin"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-show="this.algorithm!=='乘法'&&this.algorithm!=='除法'">
          <div class="title" style="width: 25.5%">分母：</div>
          <el-form-item label="最大值：">
            <el-input v-model="tableRulesForm.denominator"></el-input>
          </el-form-item>
          <el-form-item label="最小值：">
            <el-input v-model="tableRulesForm.denominatorMin"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 style="justify-content: flex-start" v-show="this.algorithm==='乘法'">
          <div class="title" style="width: 13%;">第一个数：</div>
          <el-radio-group v-model="tableRulesForm.firstNumType" size="big" style="margin-left:7%;">
            <el-radio-button label='1'>整数</el-radio-button>
            <el-radio-button label='2'>小数</el-radio-button>
            <el-radio-button label='3'>分数</el-radio-button>
          </el-radio-group>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.firstNumType=='1'&&this.algorithm==='乘法'">
          <div class="title" style="width: 25.5%">整数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numFirst"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.firstMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numFirstLen"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.firstNumType=='2'&&this.algorithm==='乘法'" style="justify-content: flex-start">
          <div class="title" style="width: 13%">小数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numFirst"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.firstMin"></el-input>
          </el-form-item>
          <el-form-item label="倍率：" prop="onlyNum">
            <el-input v-model="tableRulesForm.firstMagnify"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.firstNumType=='3'&&this.algorithm==='乘法'">
          <div class="title" style="width: 25.5%">分数：</div>
          <el-form-item label="分母最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numFirst"></el-input>
          </el-form-item>
          <el-form-item label="分子最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.firstMin"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm"
                 style="justify-content: flex-start" v-show="this.algorithm==='乘法'">
          <div class="title" style="width: 13%;">第二个数：</div>
          <el-radio-group v-model="tableRulesForm.secondNumType" size="big" style="margin-left:7%;">
            <el-radio-button label='1'>整数</el-radio-button>
            <el-radio-button label='2'>小数</el-radio-button>
            <el-radio-button label='3'>分数</el-radio-button>
          </el-radio-group>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.secondNumType=='1'&&this.algorithm==='乘法'">
          <div class="title" style="width: 25.5%">整数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numSecond"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.secondMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numSecondLen"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.secondNumType=='2'&&this.algorithm==='乘法'" style="justify-content: flex-start">
          <div class="title" style="width: 13%">小数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numSecond"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.secondMin"></el-input>
          </el-form-item>
          <el-form-item label="倍率：" prop="onlyNum">
            <el-input v-model="tableRulesForm.secondMagnify"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.secondNumType=='3'&&this.algorithm==='乘法'">
          <div class="title" style="width: 25.5%">分数：</div>
          <el-form-item label="分母最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numSecond"></el-input>
          </el-form-item>
          <el-form-item label="分子最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.secondMin"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 style="justify-content: flex-start" v-show="this.algorithm==='乘法'">
          <div class="title" style="width: 13%;">第三个数：</div>
          <el-radio-group v-model="tableRulesForm.threeNumType" size="big" style="margin-left:7%;">
            <el-radio-button label='1'>整数</el-radio-button>
            <el-radio-button label='2'>小数</el-radio-button>
            <el-radio-button label='3'>分数</el-radio-button>
          </el-radio-group>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.threeNumType=='1'&&this.algorithm==='乘法'">
          <div class="title" style="width: 25.5%">整数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numThree"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.threeMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numThreeLen"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.threeNumType=='2'&&this.algorithm==='乘法'" style="justify-content: flex-start">
          <div class="title" style="width: 13%">小数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numThree"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.threeMin"></el-input>
          </el-form-item>
          <el-form-item label="倍率：" prop="onlyNum">
            <el-input v-model="tableRulesForm.threeMagnify"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-if="tableRulesForm.threeNumType=='3'&&this.algorithm==='乘法'">
          <div class="title" style="width: 25.5%">分数：</div>
          <el-form-item label="分母最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numThree"></el-input>
          </el-form-item>
          <el-form-item label="分子最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.threeMin"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-show="this.algorithm==='除法'">
          <div class="title" style="width: 25.5%">第一个整数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numFirst"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.firstMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numFirstLen"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules"
                 v-show="this.algorithm==='除法'">
          <div class="title" style="width: 25.5%">第二个整数：</div>
          <el-form-item label="最大值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numSecond"></el-input>
          </el-form-item>
          <el-form-item label="最小值：" prop="onlyNum">
            <el-input v-model="tableRulesForm.secondMin"></el-input>
          </el-form-item>
          <el-form-item label="数位长度：" prop="onlyNum">
            <el-input v-model="tableRulesForm.numSecondLen"></el-input>
          </el-form-item>
        </el-form>
        <el-form slot="form" :model="tableRulesForm" ref="tableRulesForm" :rules="tableRules">
          <el-form-item label="题目数位要求：">
            <el-input v-model="tableRulesForm.elementRulers"></el-input>
          </el-form-item>
        </el-form>
      </dialog-add-form>

      <span slot="footer" class="dialog-footer">
                    <el-button size="mini" @click="closeTableRule()">取 消</el-button>
                    <el-button size="mini" type="primary" @click="saveTableRule()">确 定</el-button>
                  </span>
    </el-dialog>
    <!--  排序页面  -->
    <el-dialog
      title="知识点排序"
      width="50%"
      :destroy-on-close="true"
      :append-to-body="true"
      :close-on-click-modal="false"
      class=" dialog-style dialog-basic"
      :visible.sync="orderTableDialog">
      <div class="btn-sort">
        <el-button type="primary" icon="el-icon-top" size="mini" @click="moveUp">上移</el-button>
        <el-button type="primary" icon="el-icon-bottom" size="mini" @click="moveDown">下移</el-button>
        <!-- <span class="span-tit">当前材料类型：</span> -->
      </div>
      <div style="padding:10px 0 0 0">
        <el-table
          border
          stripe
          slot="table"
          :data="tableData1"
          height="100%"
          row-key="classificationId"
          :cell-style="{'text-align':'center'}"
          :header-cell-style="{'text-align':'center'}"
          @selection-change="handleSelectionChange2"
          ref="tableData1">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="classificationMsg"
            label="分类名称" :min-width="getMaxLength">
            <template slot-scope="scope">
              <span>{{ scope.row.classificationMsg }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="classificationNickname"
            label="分类别名"
            width="150">
          </el-table-column>
          <el-table-column
            prop="example"
            label="例题"
            width="180">
            <template slot-scope="scope">
              <p v-for="(item,index) in scope.row.popupList" :key="index"
                 style="display: flex;flex-direction: column">
                <span>{{ item.formulaOne }}</span>
                <span>{{ item.formulaTwo }}</span>
              </p>
            </template>
          </el-table-column>
          <el-table-column
            prop="classificationDatarange"
            label="数据范围"
            width="80">
          </el-table-column>
          <el-table-column
            prop="classificationTime"
            label="限制用时"
            width="80">
          </el-table-column>
          <el-table-column
            prop="diagnosisMsg"
            label="诊断"
            min-width="180">
          </el-table-column>
          <el-table-column
            prop="questionsNumber"
            label="出题数"
            width="80">
          </el-table-column>
          <el-table-column
            prop="content"
            label="与整数运算的对应"
            v-if="flag" width="250">
            <template slot-scope="scope">
              <p v-for="(item,index) in scope.row.popupList" :key="index"
                 style="display: flex;flex-direction: column">
                <span>{{ item.content }}</span>
              </p>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="orderTableDialog=false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveOrderNumber">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {
  removeklc,
  getKLCByParams,
  getSelectErById,
  getTreeById,
  insertEr,
  insertKLC, updateKLC,setOrderNum
} from '@/api/usually/knowledgemaintain/knowledge'
import ContainerTop from "@/components/public/MainBody/ContainerTop";
import SlotTable from "@/components/public/table/SlotTable";
import DialogAddForm from "@/components/public/DialogBody/DialogAddForm";

export default {
  name: "index",
  components: {DialogAddForm, SlotTable, ContainerTop, MainFlexibleTree},
  created() {
    this.leftDepTree("-", "classfiy", 1, 20);
    this.getTable('', 1, 20);
  },
  computed: {
    //取表格知识点名称字数最多的宽度
    getMaxLength() {
      let itemArr = [];
      let maxLength = 0;
      if (this.tableData === []) {
        return false;
      } else {
        this.tableData.forEach(item => {
          var arr = [];
          if (item.children) {
            item.children.forEach(childItem => {
              arr.push(childItem.classificationMsg.length)
            })
          }
          itemArr.push(item.classificationMsg.length);
          itemArr.push(...arr);
          for (let i = 0; i < itemArr.length; i++) {
            if (maxLength < itemArr[i]) {
              maxLength = itemArr[i]
            }
          }
        })
        if (this.flag) {
          return maxLength * 17
        } else {
          return maxLength * 12
        }
      }
    },
  },
  data() {
    return {
      orderTableDialog: false, //排序弹窗
      OrderCheckData:[],
      tableData1:[],
      treeData: [], //tree树数据
      TreeDefaultProps: {
        children: 'children',
        label: 'fullName'
      },
      nodeLevel: '', //tree树节点等级
      classfiyCode: '',
      defaultExpand: true, //tree树默认展开所有节点
      addTableDialog: false, //表格新增弹框（除小数）
      addTableDialogXiao: false, //表格新增弹框（小数）
      updateTableDialog: false, //表格编辑弹框（除小数）
      updateTableDialogXiao: false, //编辑分类弹窗（小数）
      tableRulesDialog: false, //设置规则弹框(除分数之外）
      tableRulesDialogFen: false, //设置规则弹框(分数）
      tableData: [],
      select: [],  //表格勾选框的值
      knowledge: {
        popupList: [{
          formulaOne: '',
          formulaTwo: '',
          content: '',
        }]
      },
      eparentCode: '',
      tableRulesForm: {
        byRegulationName: '',
        jinOrTuiName:'',
        regulationForm: [{
          max: '',
          min: '',
          digit: '',
          per: '',
        }]

      },
      formRules: {
        classificationMsg: [
          {required: true, message: '请输入知识点名称', trigger: 'blur'},
        ],
        classificationTime: [
          {type: 'number', message: '限制用时为数字值'}
        ],
        questionsNumber: [
          {type: 'number', message: '出题数为数字值'}
        ],
        classificationDatarange: [
          {type: 'number', message: '数据范围为数字值'}
        ],
        popupList: [
          {required: true, message: '请输入与整数运算的对应', trigger: 'blur'},
        ],
      },
      tableRules: {
        onlyNum: [
          {type: 'number', message: '请输入数值'}
        ],
      },
      numberField: '',  //数域
      algorithm: '',  //运算法则
      algorithmId: '',  //运算法则
      numType: '',    //运算类型
      jinOrTuiName: this.jinOrTuiName,  //进位或退位名称
      byRegulationName: '',  //第一个数名称
      regulationName: '',   //第二个数名称
      knowledgeInput: '', //知识点输入框
      flag: false,  //判断表格是否有与整数运算对应的列
      fenShu: false, //判断数域是否为分数
      jinOrTui: false,  //判断是否有进位和退位选择
      pageSize: 20,
      pageNum: 1,
      total: 1,
    }
  },
  methods: {
    saveOrderNumber(){
      let ids = "";
      for (let i = 0; i < this.tableData1.length; i++) {
        ids+=this.tableData1[i].classificationId+",";
      }
      setOrderNum({ids:ids}).then(reponse=>{
        this.$message.success("保存成功");
        this.orderTableDialog=false;
        this.getTable(this.classfiyCode, this.pageNum, this.pageSize);
      })
    },
    handleSelectionChange2(val){
      this.OrderCheckData = val;
    },
    moveUp() {
      if (this.OrderCheckData.length != 1) {
        this.$message({
          type: "warning",
          message: "请勾选一条数据"
        })

      } else {
        let checkData = this.OrderCheckData[0];
        let tableDatas = JSON.parse(JSON.stringify(this.tableData1));
        for (let n in tableDatas) {
          if (tableDatas[n].classificationId == checkData.classificationId) {
            if (n != 0) {
              let nextData = tableDatas[(n - 1)];
              tableDatas[n - 1] = checkData;
              tableDatas[n] = nextData;
              this.tableData1 = tableDatas;
            }
          }
        }
      }
    },
    moveDown() {
      if (this.OrderCheckData.length != 1) {
        this.$message({
          type: "warning",
          message: "请勾选一条数据"
        })

      } else {
        let checkData = this.OrderCheckData[0];
        let tableDatas = JSON.parse(JSON.stringify(this.tableData1));
        for (let n in tableDatas) {
          if (tableDatas[n].classificationId == checkData.classificationId) {
            if (n != tableDatas.length - 1) {
              let nextData = tableDatas[Number(n) + 1];
              tableDatas[Number(n) + 1] = checkData;
              tableDatas[n] = nextData;
              this.tableData1 = tableDatas;
              return;
            }
          }
        }
      }
    },
    leftDepTree(eparentCode, dictType, pageNum, pageSize) {
      //获取左侧tree树数据
      getTreeById({eparentCode, dictType, pageNum, pageSize}).then(response => {
        if (response.code === 200) {
          this.treeData = response.rows   //tree树第一级节点
        }
      })
    },
    getTable(classfiyCode, pageNum, pageSize) {
      this.tableData = [];
      getKLCByParams({classfiyCode, pageNum, pageSize}).then(res => {
        if (res.code === 200) {
          if (res.rows === null || res.rows.length === 0) {
            this.$message({
              type: 'warning',
              message: '很抱歉，暂无数据，请先增加一条知识点!'
            });
          } else {
            this.tableData = res.rows;
            this.pageNum = res.pageNum;
            this.pageSize = res.pageSize;
            this.total = res.total;
            this.tableData.forEach((item, index) => {
              item.parentIndex = index + 1;  //table的tree树节点索引
              if (item.children.length > 0) {    //如果table的当前行有children子节点，则当前行的hasChildren为true
                item.hasChildren = true;
              }
            });
          }
        } else if (res.status === 500) {
          this.$message({
            type: 'warning',
            message: '很抱歉，暂无数据，请先增加一条知识点!'
          });
        }
      }).catch(err => {
        console.log('err', err)
        if (err.status === 500) {
          this.$message({
            type: 'warning',
            message: '很抱歉，暂无数据，请先增加一条知识点!'
          })
        }
      })
    },
    addClass({row, column, rowIndex, columnIndex}) {
      if (columnIndex === 2) {
        return 'treeCell'
      }
      if (column.label === '诊断') {
        return 'diagnosisMsg'
      }
      if (column.label === '与整数运算的对应') {
        return 'operate'
      }
    },
    //table树形结构懒加载
    load(tree, treeNode, resolve) {
      setTimeout(() => {
        getKLCByParams({
          classfiyCode: this.classfiyCode,
          classificationId: tree.classificationId,
          pageNum: 1,
          pageSize: 20
        }).then(res => {
          console.log('load', res);
          tree.children = res.rows
          resolve(tree.children)
        })
      }, 300)
    },
    handleNodeClick(val, node, component) {
      this.nodeLevel = node.level;
      this.classfiyCode = val.dictCode;
      getTreeById({eparentCode: val.dictCode, dictType: "classfiy", pageNum: 1, pageSize: 20}).then(response => {
        val.children = response.rows
      })
      this.getTable(val.dictCode, this.pageNum, this.pageSize);
      if (node.level === 1) {
        this.numberField = val.fullName;
      } else if (node.level === 2) {
        this.numberField = node.parent.data.fullName;
        this.algorithm = val.fullName;
        this.algorithmId = val.dictCode.slice(-1);
      } else if (node.level === 3) {
        this.numberField = node.parent.parent.data.fullName;
        this.algorithmId = node.parent.data.dictCode.slice(-1);
        this.algorithm = node.parent.data.fullName;
        this.numType = val.fullName
        if (val.fullName.includes('进位') || val.fullName.includes('退位')) {
          if (val.fullName.includes('进位')) {
            this.jinOrTuiName = '进位要求：'
            this.carryRulers = 1;
          } else if (val.fullName.includes('退位')) {
            this.jinOrTuiName = '退位要求：'
            this.carryRulers = 1;
          } else {
            this.jinOrTuiName = ''
          }
          if (!(val.fullName.includes('不进位') || val.fullName.includes('不退位'))) {
            this.jinOrTui = true;
            this.carryRulers = 2;
          } else {
            this.jinOrTui = false;
          }
        } else {
          this.jinOrTui = false;
        }
      }
      if (this.numberField === '分数') {
        this.flag = false;
        this.fenShu = true;
      } else if (this.numberField === '小数') {
        this.flag = true;
        this.fenShu = false;
      } else {
        this.flag = false;
        this.fenShu = false;
      }
      if (this.algorithm === '加法') {
        this.byRegulationName = '被加数';
      } else if (this.algorithm === '减法') {
        this.byRegulationName = '被减数';
      } else if (this.algorithm === '乘法') {
        this.byRegulationName = '被乘数';
      } else {
        this.byRegulationName = '被除数';
      }
    },
    addKnowledgeTable() {
      if (this.nodeLevel === 3) {
        this.knowledge = {
          classificationMsg: '',
          diagnosisMsg: '',
          questionsNumber: '',
          classificationDatarange: '',
          classificationTime: '',
          popupList: [{
            formulaOne: '',
            formulaTwo: '',
            content: '',
          }]
        };
        if (!this.flag) {
          this.addTableDialog = true;
          this.addTableDialogXiao = false;
        } else {
          this.addTableDialogXiao = true;
          this.addTableDialog = false;

        }
      } else {
        this.$message({
          type: 'warning',
          message: '请先选择左侧运算类型节点!'
        });
        this.addTableDialog = false;
      }
    },
    addTableNode() {
      if (this.select.length == 0 || this.select === undefined) {
        insertKLC({
          knowledgeListClassification: this.knowledge,
          eparentCode: this.classfiyCode.toString(),
          popups: JSON.stringify(this.knowledge.popupList),
          diagnosisMsg: this.knowledge.diagnosisMsg.toString(),
        }).then(res => {
          console.log('resid1111', res)
          if (res.code === 200) {
            this.$message({
              message: '成功新增一条知识点',
              type: 'success'
            });
            this.getTable(this.classfiyCode, this.pageNum, this.pageSize);
          } else {
            this.$message({
              message: '很抱歉，新增数据失败',
              type: 'error'
            });
          }
          this.addTableDialog = false;
        })
      } else {
        this.knowledge.pid = this.select[0].classificationId;
        insertKLC({
          knowledgeListClassification: this.knowledge,
          eparentCode: this.classfiyCode.toString(),
          popups: JSON.stringify(this.knowledge.popupList),
          diagnosisMsg: this.knowledge.diagnosisMsg.toString(),
        }).then(res => {
          console.log('resid', res)
          if (res.code === 200) {
            this.$message({
              message: '成功新增一条知识点',
              type: 'success'
            });
            this.getTable(this.classfiyCode, this.pageNum, this.pageSize);
          } else {
            this.$message({
              message: '很抱歉，新增数据失败',
              type: 'error'
            });
          }
          this.addTableDialog = false;
          this.select = [];
          this.addTableDialogXiao = false;
        })
      }
    },
    updateKnowledgeTable() {
      this.knowledge = {};
      if (this.nodeLevel !== 3) {
        this.$message({
          type: 'warning',
          message: '请先选择左侧运算类型节点!'
        });
      } else {
        if (this.select.length !== 1) {
          this.$message({
            type: 'warning',
            message: '请选择一条知识点!'
          });
        } else {
          this.knowledge = this.select[0];
          // this.knowledge.classificationNickname=this.select[0].classificationNickname;
          // console.log('this', this.select);
          // console.log('knowledge', this.knowledge)
          // if (this.select[0].popupList.length < 1 || this.select[0].popupList === undefined) {
          //   this.knowledge.popupList = [{
          //     formulaOne: '',
          //     formulaTwo: '',
          //     content: '',
          //   }]
          // } else {
          //   this.knowledge.popupList.forEach(item => {
          //     this.select[0].popupList.forEach(selectItem => {
          //       item.formulaOne = selectItem.formulaOne;
          //       item.formulaTwo = selectItem.formulaTwo;
          //       item.content = selectItem.content;
          //     })
          //   })
          // }
          if (!this.flag) {
            this.updateTableDialogXiao = false;
            this.updateTableDialog = true;
          } else {
            this.updateTableDialogXiao = true;
            // console.log('knowledge', this.knowledge)
            this.updateTableDialog = false;
          }
        }
      }
    },
    updateTableNode() {
      console.log(this.knowledge)
      this.$refs.knowledgeValidateForm.validate((valid) => {
        if (!valid) {
          return false;
        }
        updateKLC({
          knowledgeListClassification: JSON.stringify(this.knowledge),
          diagnosisMsg: this.knowledge.diagnosisMsg.toString(),
        }).then(res => {
          console.log('res222', res)
          if (res.code === 200) {
            this.$message({
              message: '编辑知识点成功',
              type: 'success'
            });

          }
          this.select = [];
          this.updateTableDialogXiao=false;
          this.updateTableDialog=false;
          this.getTable(this.classfiyCode, this.pageNum, this.pageSize);
          console.log(this.tableData)
        })
      })
    },
    delKnowledgeTable() {
      if (this.nodeLevel !== 3) {
        this.$message({
          type: 'warning',
          message: '请先选择左侧运算类型节点!'
        });
      } else {
        if (this.select.length === 0) {
          this.$message({
            type: 'warning',
            message: '请至少选择一条知识点!'
          });
        } else {
          let ids='';
          for (let i = 0; i <  this.select.length; i++) {
            ids = ids+this.select[i].classificationId+',';
          }
          this.$confirm('是否永久删除选中的' + this.select.length + '条知识点', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(res => {
            // console.log(this.tableData.indexOf(this.knowledgeTable[0]));
            var id=this.select[0].classificationId;
            removeklc({ids:ids}).then(response => {
              // console.log('response',response)
              if (response.code === 200) {
                this.$message.success(response.msg)
                this.getTable(this.classfiyCode, this.pageNum, this.pageSize);
              }else{
                this.$message.erroe(response.msg)
              }
            })
          }).catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
        }
      }
    },
    orderKnowledgemaintain(){
      if (this.select.length>1){
        this.$message({
          type: 'warning',
          message: '请勾选一条数据!'
        });
        return;
      }else if (this.select.length==1){
        if (this.select[0].children.length==0){
          this.$message({
            type: 'warning',
            message: '知识点下无子节点!'
          });
          return;
        }
        this.tableData1 = JSON.parse(JSON.stringify(this.select[0].children))
        this.orderTableDialog = true;
        return;
      }
      if (this.select.length==0&&this.nodeLevel !== 3) {
        this.$message({
          type: 'warning',
          message: '请先选择左侧运算类型节点，或勾选一个知识点!'
        });
        return;
      }
      if (this.nodeLevel == 3&&this.select.length==0){
        let tableData = JSON.parse(JSON.stringify(this.tableData))
        for (let n in tableData) {
          tableData[n].children=[];
        }
        this.tableData1 = JSON.parse(JSON.stringify(this.tableData));
        this.orderTableDialog=true;
      }
    },
    //移除form表单项
    removeRegulation(form, item, key) {
      if (key === 0) {
        return;
      }
      var index = form.indexOf(item)
      if (index !== -1) {
        form.splice(index, 1)
        this.tableRulesForm.excerciseElementNum = form.length + 1;
      }
    },
    //新增form表单项
    addRegulationName(form, item) {
      item = '';
      form.push(Object.assign({}, item));
    },

    handleSelectionChange(val) {
      this.select = val;
    },
    //表格行操作，设置规则
    handleClickBtn(row) {
      if (this.nodeLevel !== 3) {
        this.$message({
          type: 'warning',
          message: '请先选择左侧运算类型节点!'
        });
        this.tableRulesDialog = false;
        this.tableRulesDialogFen = false;
      } else {
        getSelectErById({
          classifyId: row.classificationId,
        }).then(res => {
          console.log('res', res)
          if (this.numberField === '分数') {
            this.tableRulesDialog = false;
            this.tableRulesDialogFen = true;
          } else {
            this.tableRulesDialog = true;
            this.tableRulesDialogFen = false;
          }
          if (!res.data) {
            this.tableRulesForm = {
              excerciseRuleId:'',
              byRegulationName: this.byRegulationName,
              firstNumType: 3,
              secondNumType: 3,
              threeNumType: 3,
            }
          } else {
            this.tableRulesForm = res.data;
            this.tableRulesForm.digitPostion = res.data.digitPostion.toString();
            this.tableRulesForm.carryRulers = res.data.carryRulers.toString();
            this.tableRulesForm.excerciseRuleId=res.data.excerciseRuleId;
            this.tableRulesForm.elementRulers= res.data.elementRulers;
          }
          this.tableRulesForm.pid = row.classificationId;
          this.tableRulesForm.numberField = this.numberField;
          this.tableRulesForm.algorithm = this.algorithm;
          this.tableRulesForm.algorithmId = this.algorithmId;
          this.tableRulesForm.numType = this.numType;
          this.tableRulesForm.classificationMsg = row.classificationMsg;
          this.tableRulesForm.jinOrTuiName = this.jinOrTuiName;
          this.tableRulesForm.byRegulationName = this.byRegulationName;
        })
      }
    },
    saveTableRule() {
        if (this.numType === '不可约分') {
          this.tableRulesForm.carryRulers = '1'
        } else if (this.numType === '可约分') {
          this.tableRulesForm.carryRulers = '2'
        } else if (this.numType === '同分母') {
          this.tableRulesForm.carryRulers = '1'
        } else if (this.numType === '异分母') {
          this.tableRulesForm.carryRulers = '2'
        }
      insertEr({
        rules: JSON.stringify(this.tableRulesForm)
      }).then(response => {
        if (response.code === 200) {
          this.$message({
            message: '设置规则成功',
            type: 'success'
          });
          console.log(this.tableRulesForm)
          this.tableRulesDialog = false;
          this.tableRulesDialogFen = false;
        }
      })
    },
    closeTableRule(formName) {
      this.addTableDialogXiao=false;
      this.addTableDialog=false;
      this.updateTableDialog=false;
      this.updateTableDialogXiao=false;
      this.tableRulesDialog = false;
      this.tableRulesDialogFen=false;
      this.$refs[formName].resetFields();
    },
    handleChange(pageSize, pageNum) {
      // console.log(pageSize, pageNum)
      this.pageSize = pageSize;
      this.pageNum = pageNum;
      this.getTable(this.classfiyCode, this.pageNum, this.pageSize)
    },
  }
}
</script>

<style lang="scss" scoped>
.mainTop {
  height: 49px;

  /deep/ .ipt {
    display: none;
  }
}

.mainCenter {
  height: calc(100% - 49px);

  /deep/ .el-table {
    .cell {
      word-break: break-word;
      white-space: pre-line;
    }

    .treeCell {
      .cell {
        text-align: left;

        .el-table__placeholder {
          display: none;
        }

        .el-table__expand-icon {
          position: relative;
          left: -10px;
        }
      }
    }

    .operate {
      .cell {
        span {
          text-align: left;
        }
      }
    }
  }

  /deep/ .diagnosisMsg {
    .cell {
      text-align: left;
    }
  }
}

/deep/ .addTableDialog {
  .el-form-item__label {
    min-width: 120px !important;
  }
}

/deep/ .addTableDialogXiao {
  .el-form {
    flex-direction: row;
    width: 90%;
    margin: 0 auto;
    min-height: 80px !important;

    .el-form-item__label {
      min-width: 160px !important;
    }

    .popupsItem {
      width: 70%;
    }
  }

  .popupList {
    min-height: 90px !important;
    margin-left: 6% !important;

    .el-form-item__label {
      min-width: 150px !important;
    }

    .el-textarea__inner {
      min-height: 80px !important;
    }

    .liti {
      width: 50%;
      margin-left: 6%;

      .el-form-item__label {
        min-width: 100px !important;
      }
    }

    .popupsBtn {
      width: 150px;

      .el-form-item__content {
        height: 90px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;

        .el-button + .el-button {
          margin-left: 0;
        }

        .el-button--medium {
          padding: 0;
        }

        .el-button {
          padding: 12px 20px;
        }
      }
    }
  }
}

/deep/ .tableRulesDialog {
  .el-form {
    flex-direction: row !important;
    min-height: 70px !important;
    width: 90% !important;
    margin: 0 auto !important;

    .el-form-item {
      height: 40px !important;
    }

    .el-form-item__label {
      min-width: 140px !important;
    }

    .systematicName {
      width: 170% !important;
    }

    .title {
      font-weight: 600;
      font-size: 15px;
      width: 220px;
      text-align: right;
    }
  }

  .zhenduan {
    height: 70px;
    justify-content: flex-start;
    margin-bottom: 10px !important;

    .el-textarea {
      width: 121% !important;
    }
  }
}

</style>
