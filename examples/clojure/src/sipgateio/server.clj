;;
;; This assumes you are using leiningen (http://leiningen.org/)
;; You can then run this project via
;; $ lein run
;;
;; For complete example (including project file) please look at
;; https://github.com/sipgate/sipgate.io/tree/master/examples/clojure
;;

(ns sipgateio.server
  (:gen-class))

(use 'ring.adapter.jetty
     'ring.middleware.params)

(defn handler [{params :params}]
  (println (str "Call from " (params "from") " to " (params "to")))
  {:status 200 :body "I love the JVM" })

(defn -main
  [& args]
  (run-jetty (wrap-params handler) {:port 3000}))

