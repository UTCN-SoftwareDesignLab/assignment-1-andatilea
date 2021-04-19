<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="createRegularUser">Create Regular User</v-btn>
      <v-btn primary large block @click="attemptChangeView"
      >Switch View</v-btn
      >
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="updateUser">
      ></v-data-table
    >

    <RegularUserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
    ></RegularUserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import router from "../router";
import RegularUserDialog from "../components/RegularUserDialog";
export default {
  name: "UserList",
  components: { RegularUserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    updateUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },

    createRegularUser() {
      this.selectedUser = {};
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },

    attemptChangeView() {
      router.push("/books");
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
