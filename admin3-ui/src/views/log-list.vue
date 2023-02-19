<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-select v-model="query.typeNames" placeholder="操作类型" class="handle-select mr10" style="width: 150px"
                   @change="handleSelectTypeInfoChange" filterable multiple clearable>
          <el-option
            v-for="item in typeInfo"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button type="primary" :icon="Refresh" @click="handleRefresh">刷新日志</el-button>
        <el-button type="danger" :icon="Delete" @click="handleCleanLogs" v-action:log:clean>清空日志</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="user.username" label="用户" width="120"></el-table-column>
        <el-table-column prop="typeNameLabel" label="操作类型" width="120">
          <template #default="{ row }">
            <span>{{ typeInfo?.find(t => t.value === row.typeName)?.label }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="说明"></el-table-column>
        <el-table-column prop="occurredOn" label="操作时间"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :current-page="query.pageIndex"
          :page-size="query.pageSize"
          :total="pageTotal"
          @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import {Delete, Refresh} from '@element-plus/icons-vue';
import {cleanLogs, getLogList} from "../api/log";
import {getEventTypes} from "../api/common";

interface TableItem {
  id: number;
  user: { id: number; username: string; };
  typeName: string;
  typeNameLabel?: string;
  content: string;
  occurredOn: string;
}

interface TypeInfo {
  label: string;
  value: string;
}

const query = reactive({
  typeNames: [],
  pageIndex: 1,
  pageSize: 10
});

const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const typeInfo = ref<TypeInfo[]>();

//获取类型
const getTypeInfo = () => {
  getEventTypes().then(res => {
    typeInfo.value = res.data;
  });
};
getTypeInfo();
// 获取表格数据
const getData = () => {
  getLogList({
    page: query.pageIndex,
    size: query.pageSize,
    typeNames: query.typeNames.join() || undefined
  }).then(res => {
    tableData.value = res.data.list;
    pageTotal.value = res.data.total;
  });
};
getData();
// 查询操作
const handleSelectTypeInfoChange = () => {
  query.pageIndex = 1;
  getData();
};
// 分页导航
const handlePageChange = (val: number) => {
  query.pageIndex = val;
  getData();
};
// 刷新日志
const handleRefresh = () => {
  getData();
  ElMessage.success('刷新成功');
}
// 清空日志
const handleCleanLogs = () => {
  // 二次确认删除
  ElMessageBox.confirm('确定要清空所有日志吗？', '提示', {
    type: 'warning'
  }).then(() => {
    cleanLogs().then(_ => {
      ElMessage.success('清空成功');
      getData();
    });
  })
    .catch(() => {
    });
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.mr10 {
  margin-right: 10px;
}
</style>
