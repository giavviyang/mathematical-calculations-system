<script>
export default {
  name: 'AuthRedirect',
  created() {
    const hash = window.location.search.slice(1)
    if (window.localStorage) {
      window.localStorage.setItem('x-admin-oauth-code', hash)
      alert("------------------------------------------------")
      alert("------------------------------------------------"+JSON.stringify(hash))
      window.close()
    }
  },
  render: function(h) {
    return h() // avoid warning message
  }
}
</script>
