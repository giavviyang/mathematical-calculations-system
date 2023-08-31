<template>
  <!-- 高级检索 -->
  <div class="AdvancedSearch">
    <div class="SearchLeft">
      <div class="SearchForm">
        <slot name="SearchForm"/>
      </div>
      <div class="SearchTable">
        <slot name="SearchTable"/>
      </div>
      <div class="SearchClear">
        <slot name="SearchClear"/>
      </div>
    </div>
    <div class="SearchRight">
      <div class="SearchBtn">
        <slot name="SearchBtn"/>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "DialogAdvanced"
}
</script>

<style lang="scss" scoped>
.AdvancedSearch{
  border:2px #bbb dashed;
  width: 100%;
  height: 100%;
  display: flex;
  .SearchLeft{
    width:85%;
    .SearchForm{
      width: 100%;
      height: 70px;
      /deep/.el-form{
        width: 100%;
        display: flex;
        justify-content: space-around;
        flex-flow: row nowrap;
        .el-form-item{
          width: 32%;
          label{
            height: 28px;
            line-height: 28px;
            width: 100%;
            text-align: center;
          }
          .el-date-editor{
            width: 100%;
          }
        }
      }
    }
    .SearchTable{
      width: 99%;
      max-height:calc(100vh - 270px);
      margin-left: 0.5%;
      /deep/.el-table{
        border: 3px rgba(204, 204, 204, 0.18) solid;
        min-height: 300px;
        max-height:calc(100vh - 270px);
        text-align: center;
        /deep/.el-table__body-wrapper{
          min-height:260px;
         }
      }
    }
    .SearchClear{
      width: 99%;
      margin-left: 0.5%;
      height: 30px;
      line-height: 30px;
      a{
        color: #1EB4FF
      }
    }
  }

  .SearchRight{
    width: 15%;
    height: 100%;
    .SearchBtn{
      width: 100%;
      div{
        margin-top: 20px;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-around;
        flex-direction: column;
        /deep/.el-button {
          margin:10px;
        }
      }
    }

  }
}

</style>
