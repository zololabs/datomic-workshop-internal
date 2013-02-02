(ns cms.data
  (:require [cms.domain.author :as au]
            [cms.domain.article :as ar]))


(defn setup []
  (au/create "Siva" "s@e.com")
  (au/create "Amit" "a@e.com")
  (let [siva (au/find-by-email "s@e.com")]
    (au/add-article-with-tags siva "Datomic Workshop" "Datomic is cool" :category/tech ["database" "logic"])
    (au/add-article-with-tags siva "Walking around" "Walking is good, mmkay?" :category/fun ["health" "exercise" "logic"])
    (au/add-article-with-tags siva "Sleeping in" "Laziness is functional" :category/fun ["coding" "health"])
    (au/add-article-with-tags siva "Laziness" "functional no way" :category/fun ["database"])))


;;(require '[cms.domain.author :as au] '[cms.domain.article :as ar] '[cms.domain.tag :as t] '[datomic.api :as api])