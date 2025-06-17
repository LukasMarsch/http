(ns http.req
  (:require clojure.string)
  (:require logger))

(def log (logger/init "seq" :debug))

(defn req
  ([] nil)
  ([req] (let [field (clojure.string/split
                       (nth (clojure.string/split req #"\r\n") 0)
                       #" ")]
           ((log :debug) req)
    {
      :method (nth field 0)
      :path (nth field 1)
      :version (nth field 2)
    })))
