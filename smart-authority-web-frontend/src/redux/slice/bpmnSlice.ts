import { createSlice, PayloadAction } from '@reduxjs/toolkit';

// 定义redux属性的名称和类型
export interface BpmnState {
  // 流程id
  processId: string | undefined;
  // 流程名称
  processName: string | undefined;
}

// 定义redux属性的默认值
const initialState: BpmnState = {
  processId: undefined,
  processName: undefined,
};

/**
 * 定义修改redux属性的方法
 */
export const bpmnSlice = createSlice({
  name: 'bpmn',
  initialState,
  reducers: {
    handleProcessId: (state, action: PayloadAction<string | undefined>) => {
      state.processId = action.payload || undefined;
    },
    handleProcessName: (state, action: PayloadAction<string | undefined>) => {
      state.processName = action.payload || undefined;
    },
  },
});

export const { handleProcessId, handleProcessName } = bpmnSlice.actions;

export default bpmnSlice.reducer;
