import { Message } from 'element-ui';
export function prompt(type,num) {
   if( type === '保存' ){
      if( num === 0 ){
          Message({
            message: '保存成功',
            type: 'success'
          });
      }else if( num === 1 ){
          Message.error('保存失败，请联系管理员。');
      }else if( num === 2 ){
          Message({
            message: '保存失败，请按要求填写信息。',
            type: 'warning'
          });
      }
      return
   }
   if( type === '删除' ){
      if( num === 0 ){
          Message({
            message: '删除成功',
            type: 'success'
          });
      }else if( num === 1 ){
          Message.error('删除失败，请联系管理员。');
      }else if( num === 2 ){
          Message({
            message: '请先选中一条数据，再进行操作。',
            type: 'warning'
          });
      }
      return
   }
   if( type === '新增' ){
      if( num === 0 ){
          Message({
            message: '新增成功',
            type: 'success'
          });
      }else if( num === 1 ){
          Message.error('新增失败，请联系管理员。');
      }else if( num === 2 ){
          Message({
            message: '请先选中一条数据，再进行操作。',
            type: 'warning'
          });
      }
      return
   }
   if( type === '上传' ){
      if( num === 0 ){
          Message({
            message: '上传成功',
            type: 'success'
          });
      }else if( num === 1 ){
          Message.error('上传失败，请联系管理员。');
      }else if( num === 2 ){
          Message({
            message: '请先选中一条数据，再进行操作。',
            type: 'warning'
          });
      }
      return
   }
   if( type === '审批' ){
      if( num === 0 ){
          Message({
            message: '审批成功',
            type: 'success'
          });
      }else if( num === 1 ){
          Message.error('审批失败，请联系管理员。');
      }else if( num === 2 ){
          Message({
            message: '请先选中一条数据，再进行操作。',
            type: 'warning'
          });
      }
      return
   }
   if( type === '申请' ){
      if( num === 0 ){
          Message({
            message: '申请成功',
            type: 'success'
          });
      }else if( num === 1 ){
          Message.error('申请失败，请联系管理员。');
      }else if( num === 2 ){
          Message({
            message: '请先选中一条数据，再进行操作。',
            type: 'warning'
          });
      }
      return
   }
}
