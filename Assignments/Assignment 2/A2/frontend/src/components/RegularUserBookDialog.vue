<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-form>
          <v-text-field v-model="amount" label="Amount" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">Sell Amount of Books</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "RegularUserBookDialog",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      api.books
        .sell(
          {
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          },
          this.amount
        )
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
