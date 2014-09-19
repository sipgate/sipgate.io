(require :hunchentoot)
(use-package :hunchentoot)

(define-easy-handler (incoming-handler :uri "/") (from to)
  (log-message* :info "got call from ~a to ~a~%" from to)
  "girls just wanna defun!")

(defparameter *server* (start (make-instance 'easy-acceptor
					     :port 3000)))

;; to stop server: (stop *server*)
