<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create book" : "Update / Delete book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.quantity" label="Quantity" />
          <v-text-field v-model="book.price" label="Price" />
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
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.books
          .create({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.books
          .update({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    persist2() {
      if (this.toDelete && !this.isNew) {
        api.books.delete(this.book.id).then(() => this.$emit("refresh"));
      } else {
        this.book.title = "";
        this.book.author = "";
        this.book.genre = "";
        this.book.quantity = null;
        this.book.price = null;
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },

    toDelete: function () {
      return this.book.id;
    },
  },
};
</script>

<style scoped></style>
