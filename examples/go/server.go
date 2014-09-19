package main

import (
	"fmt"
	"net/http"
)

func handler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()

	from := r.Form.Get("from")
	to   := r.Form.Get("to")

	fmt.Printf("from: %s\n", from)
	fmt.Printf("to: %s\n", to)

	fmt.Fprintf(w, "So Long, and Thanks for All the Fish!")
}

func main() {
	http.HandleFunc("/", handler)
	http.ListenAndServe(":3000", nil)
}
