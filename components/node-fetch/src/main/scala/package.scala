package node_fetch

import scala.scalajs.js
import scala.scalajs.js.annotation.*
import scala.scalajs.js.typedarray.{ArrayBuffer, Uint8Array}

type RequestInfo = String | Request

type HeadersInit =
  Headers | Sequence[Sequence[ByteString]] | OpenEndedDictionary[ByteString]

type ByteString = String

/** Keep the type simple so we don't have to pull in Blob, BufferSource, FormData, URLSearchParams. */
type BodyInit = String | js.Any

//Blob | BufferSource | FormData | String //todo: add URLSearchParams
type Sequence[T] = js.Array[T]

type OpenEndedDictionary[T] = js.Dictionary[T]

