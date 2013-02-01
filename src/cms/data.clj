(ns cms.data
  (:require [cms.domain.author :as a]
            [cms.domain.article :as ar]))


(defn setup []
  (a/create "Siva" "s@e.com")
  (a/create "Amit" "a@e.com")
  (a/add-article (a/find-by-email "s@e.com") "new databases" "datomic is cool" :category/tech))

