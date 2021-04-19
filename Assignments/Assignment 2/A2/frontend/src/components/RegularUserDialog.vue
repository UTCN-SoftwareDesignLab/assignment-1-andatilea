<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create regular User" : "Edit / Delete regular User" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.password" label="Password" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="persist2">
            {{ toDelete ? "Delete" : "Reset Fields" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "RegularUserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.users
          .create({
            username: this.user.username,
            password: this.user.password,
            email: this.user.email,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.users
          .update({
            id: this.user.id,
            username: this.user.username,
            password: this.user.password,
            email: this.user.email,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    persist2() {
      if (this.toDelete && !this.isNew) {
        api.users
          .delete( this.user.id)
          .then(() => this.$emit("refresh"));
      } else {
        this.user.username = "";
        this.user.password = "";
        this.user.email = "";
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.user.id;
    },
    toDelete: function () {
      return this.user.id;
    },
  },
};
</script>

<style scoped></style>
