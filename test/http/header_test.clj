(ns http.header-test
  (:require [clojure.test :refer :all]
            [http.header :as header]
            [clojure.string :as string]))

(deftest mimeType_test
  (is (= 0 (compare (header/mimeType "aac") "audio/aac")))
  (is (= 0 (compare (header/mimeType "html") "text/html")))
  (is (= 0 (compare (header/mimeType "js") "text/javascript")))
  (is (= 0 (compare (header/mimeType "css") "text/css")))
  (is (= 0 (compare (header/mimeType "") nil)))
  (is (= 0 (compare (header/mimeType nil) nil)))
  )

(defn logTest
  [asserts expected actual] (is (if (asserts actual expected)
                                    true
                                    ((println "expected:" (str expected) "\nactual:" (str actual))
                                     false))))


(defn =str [x y] (= 0 (compare x y)))

(deftest header_test
  (let [testheader ["Accept-Charset" "utf-8"
                    "Connection" "close"]
        testheader2 ["Accept-Charset" "utf-8"]
        testheader3 ["Accept-Charset"]]
    (logTest =str
             "Accept-Charset:utf-8\r\nConnection:close\r\n\r\n"
             (header/header testheader))
    (logTest =str
             "Accept-Charset:utf-8\r\n\r\n"
             (header/header testheader2))
    (logTest =str
             "\r\n"
             (header/header))
    (logTest =str "\r\n" (header/header testheader3))))

