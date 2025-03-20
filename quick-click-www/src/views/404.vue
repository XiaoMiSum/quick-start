<template>
  <Error @error-click="push(path)" />
</template>
<script lang="ts" setup>
defineOptions({ name: 'Error404' })
const { push } = useRouter()

const path = ref('/')
</script>
