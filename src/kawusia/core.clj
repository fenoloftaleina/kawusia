(ns kawusia.core
  (:require
    [ring.adapter.jetty :as jetty]
    [clj-http.client :as http]
    [clojure.string :as cstr]))

(defn handler [request]
  (-> "https://docs.google.com/spreadsheets/d/1QQlcTe7Ssq3Delx2HpBjKX-LgQYuMdbYG_VD7asAe98/gviz/tq?tqx=out:csv&sheet=kawusia"
      http/get
      :body
      (cstr/replace #"\"" "")
      (cstr/split #"\n")
      rand-nth
      (http/get {:as :byte-array})))

(defn -main [& [port]]
  (jetty/run-jetty #'handler {:port (or port
                                        (Integer/parseInt (System/getenv "PORT")))
                              :join? false}))


(comment
  (defonce server (-main 8080))

  )
