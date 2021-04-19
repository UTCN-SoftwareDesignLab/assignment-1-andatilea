import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/bookstore", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/bookstore", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  update(book) {
    return HTTP.patch(BASE_URL + "/bookstore", book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  delete(id) {
    return HTTP.delete(BASE_URL + "/bookstore/" + id, {
      headers: authHeader()
    }).then(() => {
      return true;
    });
  },

  sell(book, amount) {
    return HTTP.patch(BASE_URL + "/bookstore/sell/" + amount, book, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  csv() {
    return HTTP.get(BASE_URL + "/bookstore/export/CSV", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  pdf() {
    return HTTP.get(BASE_URL + "/bookstore/export/PDF", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
