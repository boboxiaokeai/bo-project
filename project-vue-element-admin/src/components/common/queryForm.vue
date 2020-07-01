<template>
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

        <el-form-item label="用户名称" prop="userName">
            <el-input
                    v-model="queryParams.userName"
                    placeholder="请输入用户名称"
                    clearable
                    size="small"
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
            />
        </el-form-item>

        <el-form-item v-for="item in queryParams" :label="item.label" :prop="item.prop">
            <el-input
                    v-if="item.type=='input'"
                    v-model="item.prop"
                    :placeholder="'请输入'+item.label"
                    clearable
                    size="small"
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
            />
            <el-select
                    v-if="item.type=='select'"
                    v-model="item.prop"
                    :placeholder="item.label"
                    clearable
                    size="small"
                    style="width: 240px"
            >
                <el-option
                        v-for="dict in item.statusOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                />
            </el-select>
            <el-date-picker
                    v-if="item.type=='date'"
                    v-model="dateRange"
                    size="small"
                    style="width: 240px"
                    value-format="yyyy-MM-dd"
                    type="daterange"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
            />
        </el-form-item>

        <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    export default {
        name: "queryForm",
        props: {
            queryParams:{}
        },
        methods: {
            handleQuery() {
                this.$emit('handleQuery');
            },
            resetQuery() {
                this.$emit('resetQuery');
            }
        }
    }
</script>

<style scoped>

</style>