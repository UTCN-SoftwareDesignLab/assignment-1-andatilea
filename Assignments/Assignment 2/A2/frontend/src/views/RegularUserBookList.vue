<template>
  <v-card>
    <v-card-title>
      Regular User Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="sellBook">
    ></v-data-table>

    <RegularUserBookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></RegularUserBookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import RegularUserBookDialog from "../components/RegularUserBookDialog";

export default {
  name: "RegularUserBookList",
  components: { RegularUserBookDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    sellBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
