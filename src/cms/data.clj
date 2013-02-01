(ns cms.data
  (:require [cms.domain.author :as au]
            [cms.domain.article :as ar]))


(defn setup []
  (au/create "Siva" "s@e.com")
  (au/create "Amit" "a@e.com")
  (au/add-article-with-tags (au/find-by-email "s@e.com") "Datomic Workshop" "Datomic is cool" :category/tech ["database" "logic"]))