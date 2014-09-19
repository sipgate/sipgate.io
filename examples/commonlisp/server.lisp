;; This example uses hunchentoot as webserver.
;; You can obtain it from http://weitz.de/hunchentoot/
;;
;; If you use quicklisp, you can simply install it by evaluating
;; (ql:quicklisp "hunchentoot")

(require :hunchentoot)
(use-package :hunchentoot)

(define-easy-handler (incoming-handler :uri "/") (from to)
  (log-message* :info "got call from ~a to ~a~%" from to)
  "girls just wanna defun!")

(defparameter *server* (start (make-instance 'easy-acceptor
					     :port 3000)))

;; to stop server: (stop *server*)
