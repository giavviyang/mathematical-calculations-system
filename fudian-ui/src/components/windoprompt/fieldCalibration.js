export function fieldCalibration(Month){
var reg = /^[0-9]*$/;
  return (rule, value, callback) => {
             if (value === '') {
               callback(new Error(this.$message.error('请输入月份')))
             } else {
               if (!reg.test(Month)) {
                  callback(new Error(this.$message.error('月份必须是2位数字')))
               }
                if(Month.length != 2){
                  callback(new Error(this.$message.error('月份必须是2位数字')))
                  return
               }

    }
  };
}
