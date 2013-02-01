(ns cms.data
  (:require [cms.domain.author :as a]
            [cms.domain.article :as ar]))


(defn setup []
  (a/create "Siva" "s@e.com")
  (a/create "Amit" "a@e.com"))