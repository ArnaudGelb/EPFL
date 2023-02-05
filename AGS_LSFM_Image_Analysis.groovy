#@ImagePlus original (label="Original Image")
#@ImagePlus edt (label="EDT Image")
#@File csv (label="CSV File")

import ij.IJ

// Sanity check
if( original.getWidth() != edt.getWidth() || original.getHeight() != edt.getHeight() || original.getStackSize() != edt.getStackSize() )
	IJ.error("Images are of different dimensions, quitting")


def ori_values = original.getStack().getImageArray()
def edt_values = edt.getStack().getImageArray()

println(ori_values.class)


if (csv.exists()) csv.delete()
// Or a writer object:
csv.withWriter('UTF-8') { writer ->
	writer.write( 'Intensity'+'\t'+'EDT'+'\n')
	(1..(original.getStackSize())).each{ slice ->
		def ori_slice = original.getStack().getProcessor(slice)
		def edt_slice = edt.getStack().getProcessor(slice)
		def npx = ori_slice.getPixelCount()
		(0..(npx-1)).each{ px ->
			def px_edt = edt_slice.getf(px as int)
			if(px_edt > 0 )
				writer.write( ori_slice.getf(px as int) +'\t'+px_edt+'\n')
		}
	println(sprintf("Saving Slice %d", slice))
	}
}
return