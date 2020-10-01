(ns kawusia.core
  (:require
    [ring.adapter.jetty :as jetty]
    [clj-http.client :as http]
    [clojure.string :as cstr]))

(defn handler [request]
  (-> "https://pastebin.com/raw/9SFTX93k"
      http/get
      :body
      (cstr/split #"\r\n")
      rand-nth
      (http/get {:as :byte-array})))

(defn -main [& [port]]
  (jetty/run-jetty #'handler {:port (or port
                                        (Integer/parseInt (System/getenv "PORT")))
                              :join? false}))


(comment
  (defonce server (-main 8080))

  )
