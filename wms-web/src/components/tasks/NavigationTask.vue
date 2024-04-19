<template>
    <div>
      <div style="margin-bottom: 5px;">
        <el-input v-model="roomName" placeholder="请输入巡检房间名" suffix-icon="el-icon-search" style="width: 200px;"
                  @keyup.enter.native="loadPost"></el-input>
        <el-select v-model="mode" placeholder="请选择巡检模式" style="margin-left: 5px;">
          <el-option
              v-for="item in navigationMode"
              :key="item"
              :label="item"
              :value="item">
          </el-option>
        </el-select>
        <el-input v-model="duration" placeholder="请输入巡检时长" suffix-icon="el-icon-search" style="width: 200px;"
                  @keyup.enter.native="loadPost"></el-input>
        <!-- <el-select v-model="createTime" placeholder="请输入巡检时间" style="margin-left: 5px;">
          <el-option
              v-for="item in goodstypeData"
              :key="item.id"
              :label="item.roomName"
              :value="item.id">
          </el-option>
        </el-select> -->
  
        <el-button type="primary" style="margin-left: 5px;" @click="doSave">提交任务</el-button>
        <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
        <el-button type="success" @click="resetParam">重置</el-button>
        <!-- <el-button type="primary" style="margin-left: 5px;" @click="add" v-if="user.roleId!=2">新增</el-button>
        <el-button type="primary" style="margin-left: 5px;" @click="inGoods" v-if="user.roleId!=2">入库</el-button>
        <el-button type="primary" style="margin-left: 5px;" @click="outGoods" v-if="user.roleId!=2">出库</el-button> -->
      </div>
      <el-table :data="tableData"
                :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                border
                highlight-current-row
                @current-change="selectCurrentChange">
        <el-table-column prop="id" label="ID" width="180">
        </el-table-column>
        <el-table-column prop="roomName" label="巡检房间名" width="250">
        </el-table-column>
        <el-table-column prop="result" label="房间货物巡检数量结果" width="250">
        </el-table-column>
        <el-table-column prop="createTime" label="创建巡检任务时间" width="250">
        </el-table-column>
        <!-- <el-table-column prop="count" label="数量" width="180">
        </el-table-column>
        <el-table-column prop="remark" label="备注">
        </el-table-column> -->
        <el-table-column prop="operate" label="操作" v-if="user.roleId!=2">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
            <el-popconfirm
                title="确定删除吗？"
                @confirm="del(scope.row.id)"
                style="margin-left: 5px;">
              <el-button slot="reference" size="small" type="danger">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20,30]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
  
      <el-dialog
          title="编辑信息"
          :visible.sync="centerDialogVisible"
          width="30%"
          center>
  
        <el-form ref="form" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="房间名" prop="roomName">
            <el-col :span="20">
              <el-input v-model="form.roomName"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="巡检结果" prop="result">
            <el-col :span="20">
              <!-- <el-select v-model="form.result" placeholder="请选择仓库" style="margin-left: 5px;">
                <el-option
                    v-for="item in storageData"
                    :key="item.id"
                    :label="item.roomName"
                    :value="item.id">
                </el-option>
              </el-select> -->
              <el-input v-model="form.result"></el-input>
  
            </el-col>
          </el-form-item>
          <el-form-item label="创建时间" prop="createTime">
            <el-col :span="20">
              <!-- <el-select v-model="form.createTime" placeholder="请选择分类" style="margin-left: 5px;">
                <el-option
                    v-for="item in goodstypeData"
                    :key="item.id"
                    :label="item.roomName"
                    :value="item.id">
                </el-option>
              </el-select> -->
              <el-input v-model="form.createTime"></el-input>
            </el-col>
          </el-form-item>
          <!-- <el-form-item label="数量" prop="count">
            <el-col :span="20">
              <el-input v-model="form.count"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-col :span="20">
              <el-input type="textarea" v-model="form.remark"></el-input>
            </el-col>
          </el-form-item> -->
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="centerDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </span>
      </el-dialog>
  
      <el-dialog
          title="出入库"
          :visible.sync="inDialogVisible"
          width="30%"
          center>
  
        <el-dialog
            width="75%"
            title="用户选择"
            :visible.sync="innerVisible"
            append-to-body>
          <SelectUser @doSelectUser="doSelectUser"></SelectUser>
          <span slot="footer" class="dialog-footer">
                      <el-button @click="innerVisible = false">取 消</el-button>
                      <el-button type="primary" @click="confirmUser">确 定</el-button>
                    </span>
        </el-dialog>
  
        <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
          <el-form-item label="物品名">
            <el-col :span="20">
              <el-input v-model="form1.goodsname" readonly></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="申请人">
            <el-col :span="20">
              <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="数量" prop="count">
            <el-col :span="20">
              <el-input v-model="form1.count"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-col :span="20">
              <el-input type="textarea" v-model="form1.remark"></el-input>
            </el-col>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button @click="inDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="doInGoods">确 定</el-button>
    </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import SelectUser from "../user/SelectUser";
  
  export default {
    roomName: "NavigationTask",
    components: {SelectUser},
    data() {
      let checkCount = (rule, value, callback) => {
        if (value > 9999) {
          callback(new Error('数量输入过大'));
        } else {
          callback();
        }
      };
  
      return {
        user: JSON.parse(sessionStorage.getItem('CurUser')),
        storageData: [],
        navigationMode: [0, 1],
        goodstypeData: [],
        tableData: [],
        pageSize: 10,
        pageNum: 1,
        total: 0,
        roomName: '',
        result: '',
        createTime: '',
        mode: '',
        duration: '',
        centerDialogVisible: false,
        inDialogVisible: false,
        innerVisible: false,
        currentRow: {},
        tempUser: {},
        form: { // 提交
          id: '',
          roomName: '',
          result: '',
          createTime: '',
        },
        form1: {
          goods: '',
          goodsname: '',
          count: '',
          username: '',
          userid: '',
          adminId: '',
          remark: '',
          action: '1'
        },
        rules1: {},
        rules: {
          roomName: [
            {required: true, message: '请输入房间名', trigger: 'blur'}
          ],
          result: [
            {required: true, message: '请输入巡检结果', trigger: 'blur'}
          ],
          createTime: [
            {required: true, message: '请输入创建时间', trigger: 'blur'}
          ],
          count: [
            {required: true, message: '请输入数量', trigger: 'blur'},
            {pattern: /^([1-9][0-9]*){1,4}$/, message: '数量必须为正整数字', trigger: "blur"},
            {validator: checkCount, trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      doSelectUser(val) {
        console.log(val)
        this.tempUser = val
      },
      confirmUser() {
        this.form1.username = this.tempUser.roomName
        this.form1.userid = this.tempUser.id
  
        this.innerVisible = false
      },
      selectCurrentChange(val) {
        this.currentRow = val;
      },
      formatStorage(row) {
        let temp = this.storageData.find(item => {
          return item.id == row.result
        })
  
        return temp && temp.roomName
      },
      formatGoodstype(row) {
        let temp = this.goodstypeData.find(item => {
          return item.id == row.createTime
        })
  
        return temp && temp.roomName
      },
      resetForm() {
        this.$refs.form.resetFields();
      },
      resetInForm() {
        this.$refs.form1.resetFields();
      },
      del(id) {
        console.log(id)
  
        this.$axios.get(this.$httpUrl + '/wms/tasks/del?id=' + id).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
  
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadPost()
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
  
        })
      },
      mod(row) {
        this.centerDialogVisible = true
        this.$nextTick(() => {
          //赋值到表单
          this.form.id = row.id
          this.form.roomName = row.roomName
          this.form.result = row.result
          this.form.createTime = row.createTime
        //   this.form.count = row.count
        //   this.form.remark = row.remark
        })
      },
      add() {
  
        this.centerDialogVisible = true
        this.$nextTick(() => {
          this.resetForm()
  
          this.form.id = ''
        })
  
      },
      inGoods() {
        if (!this.currentRow.id) {
          alert('请选择记录');
          return;
        }
        this.inDialogVisible = true
        this.$nextTick(() => {
          this.resetInForm()
        })
  
        this.form1.goodsname = this.currentRow.roomName
        this.form1.goods = this.currentRow.id
        this.form1.adminId = this.user.id
        this.form1.action = '1'
      },
      outGoods() {
        if (!this.currentRow.id) {
          alert('请选择记录');
          return;
        }
        this.inDialogVisible = true
        this.$nextTick(() => {
          this.resetInForm()
        })
  
        this.form1.goodsname = this.currentRow.roomName
        this.form1.goods = this.currentRow.id
        this.form1.adminId = this.user.id
        this.form1.action = '2'
  
      },
      selectUser() {
        this.innerVisible = true
      },
      getCurrentTime() {
        //获取当前时间并打印
        let yy = new Date().getFullYear();
        let mm = new Date().getMonth()+1;
        let dd = new Date().getDate();
        let hh = new Date().getHours();
        let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
        let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
        if (mm >=1 && mm <= 9) {
          if (dd >= 1 && dd <= 9) this.createTime = yy+'-'+'0'+mm+'-'+'0'+dd+' '+hh+':'+mf+':'+ss;
          else this.createTime = yy+'-'+'0'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
        }
        else {
          if (dd >=1 && dd <= 9) this.createTime = yy+'-'+mm+'-'+'0'+dd+' '+hh+':'+mf+':'+ss;
          else this.createTime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
        }

        this.form.createTime = this.createTime
      },

      doSave() {
        if (this.roomName === '' || this.mode === '' || this.duration === '') alert('输入信息不完整，请检查!')
        else {
          this.form.roomName = this.roomName
          this.getCurrentTime()
          this.$axios.post(this.$httpUrl + '/wms/tasks/save', this.form).then(res => res.data).then(res => {
            console.log(res)
            if (res.code == 200) {
    
              this.$message({
                message: '巡检任务创建成功，小车正前往目的地!',
                type: 'success'
              });
              this.centerDialogVisible = false
              this.loadPost()
              this.resetForm()
            } else {
              this.$message({
                message: '操作失败！',
                type: 'error'
              });
            }
    
          })
      }
      },
      doMod() {
        this.$axios.post(this.$httpUrl + '/wms/tasks/update', this.form).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
  
            this.$message({
              message: '修改成功！',
              type: 'success'
            });
            this.centerDialogVisible = false
            this.loadPost()
            this.resetForm()
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
  
        })
      },
      save() {
        this.$refs.form.validate((valid) => {
          if (valid) {
            if (this.form.id) {
              this.doMod();
            } else {
              this.doSave();
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
  
      },
      doInGoods() {
        this.$axios.post(this.$httpUrl + '/wms/record/save', this.form1).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
  
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.inDialogVisible = false
            this.loadPost()
            this.resetInForm()
          } else {
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
  
        })
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageNum = 1
        this.pageSize = val
        this.loadPost()
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.pageNum = val
        this.loadPost()
      },
      resetParam() {
        this.roomName = '',
        this.mode = '',
        this.duration = '',
        this.resetForm()
        // this.result = ''
        // this.createTime = ''
      },
      loadPost() {
        this.$axios.post(this.$httpUrl + '/wms/tasks/listPage', {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            roomName: this.roomName,
            // createTime: this.createTime + '',
            // result: this.result + ''
          }
        }).then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.tableData = res.data
            this.total = res.total
          } else {
            alert('获取数据失败')
          }
  
        })
      },
      loadStorage() {
        this.$axios.get(this.$httpUrl + '/wms/result/list').then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.storageData = res.data
          } else {
            alert('获取数据失败')
          }
  
        })
      },
      loadGoodstype() {
        this.$axios.get(this.$httpUrl + '/wms/createTime/list').then(res => res.data).then(res => {
          console.log(res)
          if (res.code == 200) {
            this.goodstypeData = res.data
          } else {
            alert('获取数据失败')
          }
  
        })
      }
    },
    beforeMount() {
      this.loadStorage()
      this.loadGoodstype()
      this.loadPost()
  
    }
  }
  </script>
  
  <style scoped>
  
  </style>