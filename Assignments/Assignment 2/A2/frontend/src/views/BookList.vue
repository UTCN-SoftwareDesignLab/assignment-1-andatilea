<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="createBook">Create Book</v-btn>

      <v-btn primary large block @click="attemptChangeView"
      >Switch View</v-btn
      >
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="updateBook">
      ></v-data-table>
    <v-btn @click="createCSV">Create CSV Report</v-btn>
    <v-btn @click="createPDF">Create PDF Report</v-btn>
    <BookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import router from "../router";

export default {
  name: "BookList",
  components: { BookDialog },
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
    updateBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    createBook() {
      this.selectedBook = {};
      this.dialogVisible = true;
    },
    attemptChangeView() {
      router.push("/users");
    },

    createCSV(){
      api.books.csv();
    },

    createPDF(){
      api.books.pdf();
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
