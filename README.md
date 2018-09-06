# performance

- Note that field numbers in the range 1 through 15 take one byte to encode, including the field number and the field's type (you can find out more about this in [Protocol Buffer Encoding](https://developers.google.com/protocol-buffers/docs/encoding.html#structure)). Field numbers in the range 16 through 2047 take two bytes. So you should reserve the numbers 1 through 15 for very frequently occurring message elements. Remember to leave some room for frequently occurring elements that might be added in the future.
- Enumerator constants must be in the range of a 32-bit integer. Since `enum` values use [varint encoding](https://developers.google.com/protocol-buffers/docs/encoding)on the wire, **negative values are inefficient and thus not recommended**.

# syntax

- Map
  - the `key_type` can be any integral or string type (so, any [scalar](https://developers.google.com/protocol-buffers/docs/proto3#scalar) type except for floating point types and `bytes`). Note that enum is not a valid `key_type`. The `value_type` can be any type except another map.

