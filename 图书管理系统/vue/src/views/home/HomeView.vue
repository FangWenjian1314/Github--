<template>
  <div>
    <div style="float: left;padding-left: 10%">
      <p>用户名：{{ admin.username }}</p>
      <p>手机号码：{{ admin.phone }}</p>
      <p>邮箱：{{ admin.email }}</p>
      <a href="tencent://Message/?uin=2813162575">点击发起对话</a>
    </div>
    <div  style="text-align: center;">
      <h1>欢迎来到编程世界！</h1>
      <h1>编程使你更快乐！</h1>
<!--      <div v-for="(item,i) in vedioList" :key="i">-->
<!--        <video muted="muted" :id="startvideo+i" class="video" :src="item.path" type="video/mp4" poster="false.png" autoplay="autoplay"  loop="-1">-->
<!--          <p>你的浏览器不支持video标签.</p>-->
<!--        </video>-->
<!--      </div>-->
    </div>
    <div style="width: 40%;margin-left: 30%;margin-top: 10%;">
      <div class="loader">
        <svg viewBox="0 0 80 80">
          <circle id="test" cx="40" cy="40" r="32"></circle>
        </svg>
      </div>

      <div class="loader triangle">
        <svg viewBox="0 0 86 80">
          <polygon points="43 8 79 72 7 72"></polygon>
        </svg>
      </div>

      <div class="loader">
        <svg viewBox="0 0 80 80">
          <rect x="8" y="8" width="64" height="64"></rect>
        </svg>
      </div>
    </div>

  </div>
</template>

<script>
import Cookies from "js-cookie";
export default {
  data() {
    return{
      // vedioList: [{ path: "https://s3.bytecdn.cn/aweme/resource/web/static/image/index/new-tvc_889b57b.mp4" }],
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },
  created() {
  },
  mounted() {
    // for (var i = 0; i < 4; i++) {
    //   //获取视频的id
    //   var video = document.getElementById("startvideo"+i);
    //   //调用video标签的播放函数
    //   video.play();
    // }
  }
}

</script>
<style>
video {
  width: 120vh;
}
.loader {
  --path: #2f3545;
  --dot: #5628ee;
  --duration: 3s;
  width: 100px;
  height: 100px;
  position: relative;
}

.loader:before {
  content: '';
  width: 6px;
  height: 6px;
  border-radius: 50%;
  position: absolute;
  display: block;
  background: var(--dot);
  top: 37px;
  left: 19px;
  transform: translate(-18px, -18px);
  animation: dotRect var(--duration) cubic-bezier(0.785, 0.135, 0.15, 0.86) infinite;
}

.loader svg {
  display: block;
  width: 100%;
  height: 100%;
}

.loader svg rect, .loader svg polygon, .loader svg circle {
  fill: none;
  stroke: var(--path);
  stroke-width: 10px;
  stroke-linejoin: round;
  stroke-linecap: round;
}

.loader svg polygon {
  stroke-dasharray: 145 76 145 76;
  stroke-dashoffset: 0;
  animation: pathTriangle var(--duration) cubic-bezier(0.785, 0.135, 0.15, 0.86) infinite;
}

.loader svg rect {
  stroke-dasharray: 192 64 192 64;
  stroke-dashoffset: 0;
  animation: pathRect 3s cubic-bezier(0.785, 0.135, 0.15, 0.86) infinite;
}

.loader svg circle {
  stroke-dasharray: 150 50 150 50;
  stroke-dashoffset: 75;
  animation: pathCircle var(--duration) cubic-bezier(0.785, 0.135, 0.15, 0.86) infinite;
}

.loader.triangle {
  width: 100px;
}

.loader.triangle:before {
  left: 21px;
  transform: translate(-10px, -18px);
  animation: dotTriangle var(--duration) cubic-bezier(0.785, 0.135, 0.15, 0.86) infinite;
}

@keyframes pathTriangle {
  33% {
    stroke-dashoffset: 74;
  }

  66% {
    stroke-dashoffset: 147;
  }

  100% {
    stroke-dashoffset: 221;
  }
}

@keyframes dotTriangle {
  33% {
    transform: translate(0, 0);
  }

  66% {
    transform: translate(10px, -18px);
  }

  100% {
    transform: translate(-10px, -18px);
  }
}

@keyframes pathRect {
  25% {
    stroke-dashoffset: 64;
  }

  50% {
    stroke-dashoffset: 128;
  }

  75% {
    stroke-dashoffset: 192;
  }

  100% {
    stroke-dashoffset: 256;
  }
}

@keyframes dotRect {
  25% {
    transform: translate(0, 0);
  }

  50% {
    transform: translate(18px, -18px);
  }

  75% {
    transform: translate(0, -36px);
  }

  100% {
    transform: translate(-18px, -18px);
  }
}

@keyframes pathCircle {
  25% {
    stroke-dashoffset: 125;
  }

  50% {
    stroke-dashoffset: 175;
  }

  75% {
    stroke-dashoffset: 225;
  }

  100% {
    stroke-dashoffset: 275;
  }
}

.loader {
  display: inline-block;
  margin: 0 16px;
}


</style>
