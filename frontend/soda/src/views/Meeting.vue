<template>
  <div class="text-center">
    <h2>면접에 초대받으신 이메일을 입력해주세요.</h2>

    <v-form>
    <v-container>
      <v-row>
        <v-col
          cols="12"
          md="4"
        >
        </v-col>
        <v-col
          cols="12"
          md="4"
        >
          <v-text-field
            v-model="inputSessionId" v-on:keyup.enter="guestbtn"
            label="Email"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-container>
  </v-form>
    
    <v-btn rounded
      color="primary"
      dark v-show="inputSessionId" @click="guestbtn"> 입장하기 </v-btn>

    <div class="row no-gutters">
      <div id="wrapper" class="d-flex justify-content-center">
        <!-- 주최하기 버튼 -->
        <!-- <div class="p-1 bd-highlight" id="host">
          <button @click="hostbtn">주최하기</button>
        </div> -->
        <!-- 공개방 보기 버튼 -->
        <!-- <div class="p-1 bd-highlight" id="entrance">
          <button @click="openbtn">공개방 보기</button>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import Swal from "sweetalert2";
export default {
  name: "HomePage",
  components: {},
  data: () => {
    return {
      inputSessionId: "",
      ishost: false,
    };
  },
  computed: {
    ...mapGetters(["getId"]),
    ...mapState(["user"]),
    ...mapState("meetingStore", ["mySessionId", "meetingDialog"]),
  },
  methods: {
    ...mapActions("meetingStore", [
      "createSessionId",
      "checkSessionId",
      "changeMeetingDialog",
    ]),
    hostbtn() {
      this.createSessionId();
      //주최한다는 새로운 변수 추가
      this.ishost = true;
      this.changeMeetingDialog(true);
    },
    guestbtn() {
      if (!this.getId) {
        Swal.fire({
          title: "먼저 로그인을 해주세요!",
          icon: "warning",
        });
        return false;
      }
      this.checkSessionId(this.inputSessionId).then(() => {
        //주최 안한다는 새로운 변수 추가
        this.ishost = false;
        this.changeMeetingDialog(true);
      });
    },
    openbtn() {
      this.$router.push({ name: "OpenRoom" });
    },
  },
};
</script>

<style scoped>
/deep/ .v-text-field{
      width: 400px;
}
</style>
