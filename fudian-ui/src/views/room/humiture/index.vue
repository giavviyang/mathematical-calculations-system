<template>
  <div class="app-humiture">
    <el-row>
      <el-col :style="leftStyleName">
        <div v-if="leftTree1" style="height:100%" class="tree">
          <el-scrollbar style="height:100%" wrap-class="scrollbar-wrapper" class="scrollbar-border">
            <div style="height:100%" class="tree tree-style">
              <el-tree
                ref="roomNode"
                @node-click="nodeClick"
                :data="data"
                icon-class="el-icon-folder-opened"
                node-key="id"
                highlight-current
                :expand-on-click-node="false"
                :props="defaultProps"
                :default-expand-all="true"
              />
            </div>
          </el-scrollbar>
        </div>
        <div class="shrinkageDiv" @click="divClick()">
          <div :class="picClassname"></div>
        </div>

      </el-col>
      <el-col :span="20" :style="rightStyleName">
        <el-row ref="searchRow" :gutter="10">
          <el-form
            v-if="showSearchBox"
            ref="queryForm"
            class="search-form"
            v-hasPermi="['room:humiture:search']"
            :model="queryForm"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);"
          >
            <el-form-item label="检查人" style="" prop="e01zc24a">
              <el-input v-model="queryForm2.checkname" @keyup.enter.native="search" placeholder="请输入检查人姓名" clearable
                        size="mini"
                        style="width: 150px"/>
            </el-form-item>
            <el-form-item label-width="90px" label="检查日期起" prop="e01zc24a" style="margin-left: 20px;">
              <el-date-picker
                v-model="queryForm2.beginTime"
                value-format="yyyy-MM-dd"
                type="date"
                size="mini"
                style="width: 150px"
                placeholder="请选择检查日期"
              />
            </el-form-item>
            <el-form-item label="检查日期至" label-width="90px" prop="e01zc24a" style="margin-left: 20px;">
              <el-date-picker
                v-model="queryForm2.endTime"
                value-format="yyyy-MM-dd"
                type="date"
                size="mini"
                style="width: 150px"
                placeholder="请选择检查日期"
              />
            </el-form-item>

            <!--            <el-form-item label="所在位置" prop="e01zc24a">-->
            <!--              <el-input v-model="queryForm.location" placeholder="请输入所在位置" clearable size="small" style="width: 240px" />-->
            <!--            </el-form-item>-->
            <el-form-item style="margin-left: 20px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
            </el-form-item>
          </el-form>
          <el-col :span="24" style="margin-bottom: 10px">
            <el-button type="primary" icon="el-icon-plus" size="mini" v-hasPermi="['room:humiture:add']"
                       @click="addCheck">新增
            </el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['room:humiture:edit']"
                       @click="updateRoomCheck">编辑
            </el-button>
            <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['room:humiture:delete']"
                       @click="deleteRoomCheck">删除
            </el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['room:humiture:export']"
                       @click="exportExcel">导出
            </el-button>
          </el-col>

          <!--          <el-dropdown style="float: right; margin-right: 30px;">-->
          <!--            <el-button icon="el-icon-menu" size="mini" circle />-->
          <!--            <el-dropdown-menu style="padding: 10px">-->
          <!--              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="small">-->
          <!--                <el-checkbox>{{ item.label }}</el-checkbox>-->
          <!--              </el-checkbox-group>-->
          <!--            </el-dropdown-menu>-->
          <!--          </el-dropdown>-->
          <!--          <el-col :span="1.5" style="float: right;"><el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'85%'}" /></el-col>-->
        </el-row>

        <el-table :data="tableData.rows"
                  ref="table"
                  size="mini"
                  @selection-change="handleSelectionChange"
                  :height="tableHeight"
                  border
                  row-key="id"
                  stripe
                  @row-click="StepsListRowClick"
                  tooltip-effect="light"
                  :header-cell-style="headClass"
                  align="left">
          <el-table-column type="selection" width="55"/>
          <!--              <el-table-column type="expand" border="border:none">-->
          <!--                <template slot-scope="props">-->
          <!--                  <el-form label-position="left" inline class="demo-table-expand">-->
          <!--                    <el-form-item label="库房基本配置">-->
          <!--                      <span>{{ props.row.roomconfig }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="灭火设施检查">-->
          <!--                      <span>{{ props.row.devicedesc }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="防尘检查">-->
          <!--                      <span>{{ props.row.dustproof }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="通风情况">-->
          <!--                      <span>{{ props.row.airthough }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="线路检查">-->
          <!--                      <span>{{ props.row.electricline }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="易燃易爆品防护">-->
          <!--                      <span>{{ props.row.explosive }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="防蛀检查">-->
          <!--                      <span>{{ props.row.mothproof }}</span>-->
          <!--                    </el-form-item>-->
          <!--                    <el-form-item label="备注">-->
          <!--                      <span>{{ props.row.remark }}</span>-->
          <!--                    </el-form-item>-->
          <!--                  </el-form>-->
          <!--                </template>-->
          <!--              </el-table-column>-->
          <!--          <el-table-column-->
          <!--            label="序号"-->
          <!--            type="index"-->
          <!--            width="50">-->
          <!--          </el-table-column>-->
          <el-table-column show-overflow-tooltip v-for="(item, index) in col" v-if="col[index].isCol"
                           :width="item.width" :key="index"
                           align="center" :prop="col[index].prop" :formatter="item.formatter" :label="item.label"/>

          <!--              <el-table-column fixed="right" label="操作" width="100px">-->
          <!--                <template slot-scope="scope">-->
          <!--                  <el-button type="text" size="small" @click="updateRoomCheck(scope.row)">编辑</el-button>-->
          <!--                  <el-button type="text" size="small" @click="deleteRoomCheck(scope.row)">删除</el-button>-->
          <!--                </template>-->
          <!--              </el-table-column>-->
        </el-table>


        <!--          </el-col>-->
        <div style="margin-top: 5px;float: right">
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="tableData.total"
            :current-page="queryForm.pageNum  "
            @size-change="pageSize"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
      <el-dialog :title="title" :close-on-click-modal="false" style="width: 100%;margin-top: 3%"
                 class=" dialog-style dialog-basic"
                 :visible.sync="dialogFormVisible">
        <el-row>
          <el-col :span="24">
            <el-form ref="form" :rules="formRules" :model="form"
                     style="padding: 20px 0 0 0;width: 100%;margin: 0 auto;padding-left: 3%;padding-right: 3%"
                     label-width="150px">
              <el-col :span="12">
                <el-form-item prop="checkname" label="检查人">
                  <el-input style="width: 100%;" placeholder="请输入检查人" clearable size="mini" v-model="form.checkname"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="roomId" label="所属库房">

                  <!--                  <slot name="error" >{{roomJY}}</slot>-->
                  <el-select size="mini" style="width: 100%;" clearable v-model="form.roomId" placeholder="请选择所属库房">
                    <el-option

                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="checkdate" label="检查时间">
                  <el-date-picker
                    size="mini"
                    style="width: 100%;"
                    v-model="form.checkdate"
                    type="datetime"
                    placeholder="选择日期时间"
                    clearable
                    value-format="yyyy-MM-dd HH:mm:ss"
                  >
                  </el-date-picker>
                  <!--            <el-col :span="11">-->
                  <!--              <el-date-picker size="mini" v-model="form.date" type="date" placeholder="选择日期"  />-->
                  <!--            </el-col>-->
                  <!--            <el-col  :span="2"> -</el-col>-->
                  <!--            <el-col :span="11">-->
                  <!--              <el-time-picker size="mini" v-model="form.time" prop="checktime" placeholder="选择时间"  />-->
                  <!--            </el-col>-->
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="temperature" label="温度">
                  <el-input style="width: 100%;" clearable placeholder="请输入温度" size="mini" v-model="form.temperature"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="humidity" label="湿度">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入湿度" v-model="form.humidity"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="roomconfig" label="库房基本配置说明">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入库房基本配置说明" v-model="form.roomconfig" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="devicedesc" label="灭火设施检查">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入灭火设施检查" v-model="form.devicedesc" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="dustproof" label="防尘检查">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入防尘检查" v-model="form.dustproof" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="airthough" label="通风情况说明">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入通风情况说明" v-model="form.airthough" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="electricline" label="线路检查">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入线路检查" v-model="form.electricline" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="explosive" label="易燃易爆品防护说明">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入易燃易爆品防护说明" v-model="form.explosive" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="mothproof" label="防蛀检查">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入防蛀检查" v-model="form.mothproof" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="remark" label="备注">
                  <el-input style="width: 100%;" size="mini" clearable placeholder="请输入备注" v-model="form.remark" type="textarea"/>
                </el-form-item>
              </el-col>
            </el-form>
          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible = false ">取消</el-button>
          <el-button size="mini" type="primary" @click="saveAdd">保存</el-button>
        </div>
      </el-dialog>

      <el-dialog title="编辑检查记录" :close-on-click-modal="false" style="width: 100%;margin-top: 3%"
                 class=" dialog-style dialog-basic"
                 :visible.sync="dialogFormVisible1">
        <el-row>
          <el-col :span="24">
            <el-form ref="form2" :rules="formRules" :model="form"
                     style="padding: 20px 0 0 0;width: 100%;margin: 0 auto;padding-left: 3%;padding-right: 3%"
                     label-width="150px">
              <el-col :span="12">
                <el-form-item prop="checkname" label="检查人">
                  <el-input style="width: 100%;" placeholder="请输入检查人" clearable size="mini" v-model="form.checkname"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="roomId" label="所属库房">
                  <el-select style="width: 100%;" size="mini" v-model="form.roomId" placeholder="请选择所属库房" clearable>
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="checkdate" label="检查时间">
                  <el-date-picker
                    size="mini"
                    style="width: 100%;"
                    v-model="form.checkdate"
                    type="datetime"
                    clearable
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="temperature" label="温度">
                  <el-input style="width: 100%;" clearable placeholder="请输入温度" size="mini" v-model="form.temperature"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="humidity" label="湿度">
                  <el-input style="width: 100%;" clearable placeholder="请输入湿度" size="mini" v-model="form.humidity"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="roomconfig" label="库房基本配置说明">
                  <el-input style="width: 100%;" clearable placeholder="请输入库房基本配置说明" size="mini" v-model="form.roomconfig" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="devicedesc" label="灭火设施检查">
                  <el-input style="width: 100%;" clearable placeholder="请输入灭火设施检查" size="mini" v-model="form.devicedesc" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="dustproof" label="防尘检查">
                  <el-input style="width: 100%;" clearable placeholder="请输入防尘检查" size="mini" v-model="form.dustproof" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="airthough" label="通风情况说明">
                  <el-input style="width: 100%;" clearable placeholder="请输入通风情况说明" size="mini" v-model="form.airthough" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="electricline" label="线路检查">
                  <el-input style="width: 100%;" clearable placeholder="请输入线路检查" size="mini" v-model="form.electricline" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="explosive" label="易燃易爆品防护说明">
                  <el-input style="width: 100%;" clearable placeholder="请输入易燃易爆品防护说明" size="mini" v-model="form.explosive" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="mothproof" label="防蛀检查">
                  <el-input style="width: 100%;" clearable placeholder="请输入防蛀检查" size="mini" v-model="form.mothproof" type="textarea"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item prop="remark" label="备注">
                  <el-input style="width: 100%;" clearable placeholder="请输入备注" size="mini" v-model="form.remark" type="textarea"/>
                </el-form-item>
              </el-col>
            </el-form>
          </el-col>
        </el-row>

        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible1 = false ">取消</el-button>
          <el-button size="mini" type="primary" @click="saveUpdate">保存</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>

  import {queryRoomList} from '@/api/room/room'
  import {queryCheclList, addCheck, updateCheck, deleteCheck, deleteChecks} from '@/api/room/check'
  import {MessageBox, Notification} from 'element-ui'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'

  export default {
    watch: {
      tableData: {
        handler(a, b) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.table.doLayout();

          })
        },
        deep: true,
      }
    },
    data() {
      return {
        roomJY: "请选择库房",
        options: [],
        tableHeight: '0',
        select: [],
        picClassname: 'shrinkageLeft',
        rightStyle: {
          width: '85%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        rightStyle1: {
          width: '99.5%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        leftStyle: {
          width: '15%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyle1: {
          width: '0.5%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '15%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '85%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        leftTree1: true,
        dialogFormVisible1: false,
        title: '新增检查记录',
        dialogFormVisible: false,
        tableData: {
          rows: [],
          total: 0
        },
        ruleUtils: {
          notNull: {
            required: true,
            message: '输入内容不可为空',
            trigger: 'blur'
          },
          selectNotNull: {
            required: true,
            message: '请选择库房',
            trigger: 'blur'
          },
          // 判断长度（长）
          lengthLong: {
            min: 0,
            max: 40,
            message: '输入内容不得超过40个字符',
            trigger: 'blur'
          },
          // 判断长度（短）
          lengthShort: {
            min: 0,
            max: 18,
            message: '输入内容不得超过18个字符',
            trigger: 'blur'
          },
          // 判断长度（非常长）
          lengthLongLong: {
            min: 0,
            max: 200,
            message: '输入内容不得超过200个字符',
            trigger: 'blur'
          },
          // double校验 温度
          notNullDoubletem: {
            validator: (rule, value, callback) => {
              if (value != '') {
                if ((/^(\-?\d{0,2})(\.\d{0,2})?/).test(value) == false) {
                  callback(new Error('请输入负100-100的数字'))
                } else {
                  if (value >= -100 && value <= 100) {
                    callback()
                  } else {
                    callback(new Error('请输入负100-100的数字'))
                  }
                }
              } else {
                callback(new Error('输入内容不可为空'))
              }
            },
            trigger: ['change', 'blur']
          },

          // double校验 湿度
          notNullDoublehumi: {
            validator: (rule, value, callback) => {
              if (value != '') {
                if ((/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/).test(value) == false) {
                  callback(new Error('请输入0-100的数字'))
                } else {
                  if (value >= 0 && value <= 100) {
                    callback()
                  } else {
                    callback(new Error('请输入0-100的数字'))
                  }
                }
              } else {
                callback(new Error('输入内容不可为空'))
              }
            },
            trigger: ['change', 'blur']
          }
        },
        formRules: {
          checkname:[
            {required: true, message: '请输入检查人姓名', trigger: 'blur'},
            {min: 1, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur'}
          ],
          roomId:[
            {required: true, message: '请选择所属库房', trigger: 'blur'},
          ],
          checkdate:[
            {required: true, message: '请选择检查时间', trigger: 'blur'},
          ],
          temperature:[
            {required: true, message: '请输入温度', trigger: 'blur'},
            {min: 1, max: 10, message: '输入内容不得超过10个字符', trigger: 'blur'}
          ],
          humidity:[
            {required: true, message: '请输入湿度', trigger: 'blur'},
            {min: 1, max: 10, message: '输入内容不得超过10个字符', trigger: 'blur'}
          ],
          roomconfig:[
            {required: true, message: '请输入库房基本配置说明', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          devicedesc:[
            {required: true, message: '请输入灭火设施检查', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          dustproof:[
            {required: true, message: '请输入防尘检查', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          airthough:[
            {required: true, message: '请输入通风情况说明', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          electricline:[
            {required: true, message: '请输入线路检查', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          explosive:[
            {required: true, message: '请输入易燃易爆品防护说明', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          mothproof:[
            {required: true, message: '请输入防蛀检查', trigger: 'blur'},
            {min: 1, max: 300, message: '输入内容不得超过300个字符', trigger: 'blur'}
          ],
          remark:[
            {min: 1, max: 500, message: '输入内容不得超过500个字符', trigger: 'blur'}
          ]
        },
        form: {
          roomId: '',
          id: '',
          temperature: '',
          humidity: '',
          roomconfig: '',
          devicedesc: '',
          dustproof: '',
          airthough: '',
          electricline: '',
          explosive: '',
          mothproof: '',
          checkname: '',
          checkdate:new Date(),
          remark: ''
        },
        queryForm2: {
          checkname: '',
        },
        queryForm: {

          roomId: '',
          pageSize: 20,
          pageNum: 1
        },
        showSearchBox: true,
        col: [
          {
            width: 100,
            isCol: true,
            label: '检查人',
            prop: 'checkname',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 100,
            isCol: true,
            label: '所属库房',
            prop: 'roomName',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '检查时间',
            prop: 'checkdate',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 100,
            isCol: true,
            label: '温度（°c）',
            prop: 'temperature',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 100,
            isCol: true,
            label: '湿度（%）',
            prop: 'humidity',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '库房基本配置',
            prop: 'roomconfig',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '灭火设施检查',
            prop: 'devicedesc',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '防尘检查',
            prop: 'dustproof',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '通风情况',
            prop: 'airthough',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '线路检查',
            prop: 'electricline',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '易燃易爆品防护',
            prop: 'explosive',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            width: 200,
            isCol: true,
            label: '防蛀检查',
            prop: 'mothproof',
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          }
          // { isCol: true, label: '湿度（%）', prop: 'humidity' },
          // { isCol: true, label: '湿度（%）', prop: 'humidity' },
        ],
        defaultProps: {
          children: 'children',
          // hasChildren: 'hasChildren',
          label: 'roomName',
          isLeaf: 'hasChildren'
        },
        data: [],
        searchRowHeight: '',
        searchRow: 'searchRow',

      }
    },
    created() {
      this.getRoomList()
    },
    mounted() {

      this.$nextTick(() => {

        let _this = this;
        setTimeout(() => {

          _this.searchRowHeight = _this.$refs[_this.searchRow].$el.offsetHeight
          _this.tableHeight = _this.tableHeight = (document.body.clientHeight - (84 + 58 + _this.searchRowHeight)) + "px"
        }, 200)

      })

      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 58 + this.searchRowHeight)) + "px"
        })();
      };

      // this.formRules = {
      //   roomconfig: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   devicedesc: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   dustproof: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   airthough: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   electricline: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   explosive: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   mothproof: [this.ruleUtils.notNull, this.ruleUtils.lengthLongLong],
      //   remark: [this.ruleUtils.lengthLongLong],
      //   date: [this.ruleUtils.notNull],
      //   time: [this.ruleUtils.notNull],
      //   checkname: [this.ruleUtils.notNull, this.ruleUtils.lengthShort],
      //   temperature: [this.ruleUtils.notNull, this.ruleUtils.notNullDoubletem],
      //   humidity: [this.ruleUtils.notNull, this.ruleUtils.notNullDoublehumi]
      // };
      let _this = this;
      this.col[1].formatter = function (row, column, cellValue, index) {
        let value = "";
        _this.data[0].children.map(item => {
          if (item.id == row.roomId) {
            value = item.roomName;
          }
        })
        return value;
      }
    },
    methods: {
      handleSelectionChange(val) {
        this.select = val;
      },
      StepsListRowClick(row) {    //档案管理-点击行事件-方法
        this.$refs.table.clearSelection()
        if (this.select.length === 0) {
          this.$refs.table.toggleRowSelection(row)
          return;
        }
        this.$refs.table.toggleRowSelection(row)
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      divClick() {
        this.leftTree1 = !this.leftTree1
        if (this.leftTree1) {
          this.rightStyleName = this.rightStyle
          this.leftStyleName = this.leftStyle
          this.picClassname = 'shrinkageLeft'
          this.treeShow = !this.treeShow
        } else {
          this.rightStyleName = this.rightStyle1
          this.leftStyleName = this.leftStyle1
          this.picClassname = 'shrinkageRight'
          this.treeShow = !this.treeShow
        }
      },
      saveUpdate() {
        this.$refs['form2'].validate((valid) => {
          if (valid) {
            let param = JSON.parse(JSON.stringify(this.form));
            param.checkdate = this.moment(param.checkdate).format("YYYY-MM-DD HH:mm:ss")
            updateCheck(param).then(response => {
              if (response.code == 200) {
                // Notification.success({
                //   title: response.msg
                // })
                this.$message({
                  message: response.msg,
                  type: 'success'
                })
                this.resetForm2()
                this.getCheckList()
                this.dialogFormVisible1 = false
              }
            })
          }
        })
      },
      saveAdd() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            let param = JSON.parse(JSON.stringify(this.form));
            param.checkdate = this.moment(param.checkdate).format("YYYY-MM-DD HH:mm:ss")
            addCheck(param).then(response => {
              if (response.code == 200) {
                // Notification.success({
                //   title: response.msg
                // })
                this.$message({
                  message: response.msg,
                  type: 'success'
                })
                this.resetForm()
                this.getCheckList()
                this.dialogFormVisible = false
              }
            })
          }
        })
      },
      // 点击新增按钮
      addCheck() {
        // if (this.form.roomId == null || this.form.roomId == '') {
        //   // Notification.error({
        //   //   title: '请选择库房'
        //   // })
        //   this.$message({
        //     message:'请选择库房',
        //     type:'warning'
        //   })
        //
        //   return
        // }
        this.dialogFormVisible = true
        this.resetForm()
        this.form.date = new Date();
        this.form.time = new Date();
        this.form.id = ""
        this.form.humidity = ""
        this.form.temperature = ""
        this.form.remark = ""
        this.form.roomconfig = ""
        this.form.devicedesc = ""
        this.form.dustproof = ""
        this.form.airthough = ""
        this.form.electricline = ""
        this.form.explosive = ""
        this.form.mothproof = ""
        this.form.checkname = ""
      },
      // 修改
      updateRoomCheck() {

        if (this.select.length > 1) {
          this.$message({
            message: '请选择一条记录',
            type: 'warning'
          })
          return;
        }

        if (this.select.length == 0) {
          this.$message({
            message: '请选择一条记录',
            type: 'warning'
          })
          return;
        }
        let row = this.select[0];
        this.dialogFormVisible1 = true
        this.resetForm2()
        this.form.checkDate = row.checkdate
        // console.log(date)
        // console.log(date)
        this.form.roomId = row.roomId
        this.form.id = row.id
        this.form.humidity = row.humidity.toString()
        this.form.temperature = row.temperature.toString()
        this.form.remark = row.remark
        this.form.roomconfig = row.roomconfig
        this.form.devicedesc = row.devicedesc
        this.form.dustproof = row.dustproof
        this.form.airthough = row.airthough
        this.form.electricline = row.electricline
        this.form.explosive = row.explosive
        this.form.mothproof = row.mothproof
        this.form.checkname = row.checkname
        console.info(this.form)
      },
      strFormatDate(str) {
        return new Date(Date.parse(str.replace(/-/g, "/")));
      },
      exportExcel() {
        if (this.select.length == 0) {
          this.$confirm('是否导出搜索后的检查数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            queryRoomList(null).then(response => {

              this.data = [{roomName: "库房列表", children: response.data}]
              response.data.map(item => {
                let opt = {
                  value: item.id,
                  label: item.roomName
                }
                this.options.push(opt);
              })

            })
            if (this.queryForm.date != null && this.queryForm.date != '') {
              this.queryForm.beginTime = this.queryForm.date[0]
              this.queryForm.endTime = this.queryForm.date[1]
            } else {
              this.queryForm.beginTime = ''
              this.queryForm.endTime = ''
            }
            this.queryForm.pageSize = 99999999;
            queryCheclList(this.queryForm).then(response => {
              let rows = response.rows;
              for (let i = 0; i < rows.length; i++) {
                for (let j = 0; j < this.data[0].children.length; j++) {
                  if (rows[i].roomId == this.data[0].children[j].id) {
                    rows[i].roomName = this.data[0].children[j].roomName;
                  }
                }
              }
              if (response.rows.length == 0) {
                this.$message.error("无数据,导出失败");
                return;
              }
              let row = {
                row: response.rows,
                status: 2
              }
              console.log(row)
              downLoadPost("/export/excel/exportClick", row, this)
            })
          })

        } else {
          this.$confirm('是否导出选中的检查数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            for (let i = 0; i < this.select.length; i++) {
              for (let j = 0; j < this.data[0].children.length; j++) {
                if (this.select[i].roomId == this.data[0].children[j].id) {
                  this.select[i].roomName = this.data[0].children[j].roomName;
                }
              }
            }
            let row = {
              row: this.select,
              status: 2
            }
            console.log(row)
            downLoadPost("/export/excel/exportClick", row, this)
          })

        }


      },

      // 删除
      deleteRoomCheck() {
        if (this.select.length == 0) {
          this.$message({
            message: '请至少选择一条记录',
            type: 'warning'
          })
          return;
        }
        let ids = [];
        this.select.map(item => {
          ids.push(item.id);
        })
        MessageBox.confirm(
          '是否删除选中的' + this.select.length + '条数据',
          '系统提示',
          {
            confirmButtonText: '是',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          deleteChecks(ids.join(",")).then(response => {
            if (response.code == 200) {
              // Notification.success({
              //   title: response.msg
              // })
              this.$message({
                message: response.msg,
                type: 'success'
              })
              this.getCheckList()
            }
          })
        })
      },
      nodeClick(no, node, tree) {
        console.log("fdsa")
        this.queryForm.roomId = node.data.id
        this.form.roomId = node.data.id
        this.getCheckList()
      },
      getRoomList() {
        queryRoomList(null).then(response => {

          this.data = [{roomName: "库房列表", children: response.data}]
          response.data.map(item => {
            let opt = {
              value: item.id,
              label: item.roomName
            }
            this.options.push(opt);
          })
          this.getCheckList();
        })
      },
      getCheckList() {
        // if (this.queryForm.date != null && this.queryForm.date != '') {
        //   this.queryForm.beginTime = this.queryForm.date[0]
        //   this.queryForm.endTime = this.queryForm.date[1]
        // } else {
        //   this.queryForm.beginTime = ''
        //   this.queryForm.endTime = ''
        // }
        queryCheclList(this.queryForm).then(response => {
          this.tableData.rows = response.rows
          this.tableData.total = response.total
        })
      },
      search() {
        console.log(this.queryForm)
        this.queryForm.pageNum = 1;
        this.queryForm.beginTime = this.queryForm2.beginTime;
        this.queryForm.endTime = this.queryForm2.endTime;
        this.queryForm.checkname = this.queryForm2.checkname;
        this.getCheckList();
      },
      reset() {
        this.queryForm.checkname = ''
        this.queryForm.date = ''
        this.getCheckList()
      },
      pageSize(value) {
        console.log('p' + value)

        this.queryForm.pageSize = value
        if (this.tableData.total > 0) {
          this.getCheckList()
        }
      },
      pageCurrent(value) {
        console.log(value)
        this.queryForm.pageNum = value
        if (this.tableData.total > 0) {
          this.getCheckList()
        }
      },
      resetForm() {
        this.$nextTick(() => {
          this.$refs['form'].resetFields()
        })
      },
      resetForm2() {
        this.$nextTick(() => {
          this.$refs['form2'].resetFields()
        })
      }
    }
  }
</script>

<style lang="scss" scoped>

  .app-humiture {
    height: calc(100vh - 86px);
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
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
    /deep/ .el-select > .el-input {
      /*width: 163px !important;*/
    }
  }

  .dialog-style {
    /deep/ .el-dialog__body {
      padding: 10px;
    }
    /deep/ .el-dialog__header {
      background: rgba(204, 204, 204, 0.18);
      padding: 10px;
    }
    /deep/ .el-dialog__title {
      font-weight: 500;
      font-size: 15px
    }
    /deep/ .el-dialog__headerbtn {
      top: 13px;
    }

    /deep/ .el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/ .el-button + .el-button {
      margin-left: 6px;
    }

  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
  }

  .tree-style {
    /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }

  }

  .tree {
    width: 100%;
    padding: 10px 10px 26px 10px;
    /*border-right: 1px solid #e2e3e6;*/
  }

  .search-form {
    width: 99%;
    background: rgba(204, 204, 204, 0.18);
    margin-left: 5px;
    margin-bottom: 10px;
    padding-left: 10px;
    padding-top: 10px;
    padding-bottom: 10px;
    display: flex;
    .form-width {
      width: 22%;
      display: flex;
    }
    /deep/ .el-form-item {
      margin-bottom: 0 !important;
    }
  }

  .shrinkageLeft {
    border-right: 8px solid #FFFFFF;
    border-top: 8px solid transparent;
    border-bottom: 8px solid transparent;
    position: absolute;
    width: 8px;
    height: 8px;
    left: 0px;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    cursor: pointer;
  }

  .shrinkageRight {
    border-left: 8px solid #FFFFFF;
    border-top: 8px solid transparent;
    border-bottom: 8px solid transparent;
    position: absolute;
    width: 8px;
    height: 8px;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    cursor: pointer;
  }

  .shrinkageDiv {
    cursor: pointer;
    background-color: #1890ff;
    right: 0;
    bottom: 0;
    margin: auto;
    top: 0;
    height: 200px;
    width: 8px;
    border-radius: 20px;
    position: absolute;
  }

  /deep/ .el-scrollbar__wrap {
    overflow-x: hidden;
  }
</style>
